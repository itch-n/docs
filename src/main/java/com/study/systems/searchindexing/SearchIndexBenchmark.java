package com.study.systems.searchindexing;

import java.util.*;

/**
 * Benchmarks linear scan vs inverted index across growing corpus sizes.
 *
 * Both strategies search the same document set for the same queries.
 * Results show how query latency scales with corpus size under each approach:
 *
 *   Linear scan:     O(N × W) per query  — re-reads every document every time
 *   Inverted index:  O(1) per query      — one hash lookup after a one-time build
 *
 * Corpus: 500-word vocabulary, 100 words per document, seeded for reproducibility.
 */
public class SearchIndexBenchmark {

    static final int   VOCAB_SIZE    = 500;
    static final int   WORDS_PER_DOC = 100;
    static final int   NUM_QUERIES   = 500;
    static final int[] CORPUS_SIZES  = {100, 1_000, 10_000};
    static final long  SEED          = 42L;

    private static final String[] VOCAB;

    static {
        VOCAB = new String[VOCAB_SIZE];
        for (int i = 0; i < VOCAB_SIZE; i++) VOCAB[i] = "word" + i;
    }

    public static void main(String[] args) {
        System.out.println("=== Search / Indexing Benchmark ===\n");
        warmup();
        benchmarkIndexBuildCost();
        System.out.println();
        benchmarkSingleTermQuery();
        System.out.println();
        benchmarkAndQuery();
    }

    // -------------------------------------------------------------------------
    // Benchmarks
    // -------------------------------------------------------------------------

    static void warmup() {
        // Drive JIT compilation before measurement
        List<Document> corpus = buildCorpus(1_000, SEED);
        String[] queries = randomTerms(200, SEED + 99);
        LinearScanner scanner = new LinearScanner(corpus);
        SearchIndex index = new SearchIndex();
        for (Document doc : corpus) index.add(doc);
        for (String q : queries) { scanner.search(q); index.search(q); }
    }

    static void benchmarkIndexBuildCost() {
        System.out.println("--- Index Build Cost (10,000 documents) ---");
        int numDocs = 10_000;
        List<Document> corpus = buildCorpus(numDocs, SEED);

        // Linear scan has no build phase
        System.out.printf("  Linear Scan:       0 ms   (no index to build)%n");

        long start = System.nanoTime();
        SearchIndex index = new SearchIndex();
        for (Document doc : corpus) index.add(doc);
        long buildMs = msElapsed(start);

        System.out.printf("  Inverted Index:  %3d ms   (index %,d docs × %d words/doc)%n",
                buildMs, numDocs, WORDS_PER_DOC);
        System.out.printf("  Build is a one-time cost paid once at startup%n");
    }

    static void benchmarkSingleTermQuery() {
        System.out.println("--- Single-Term Query (" + NUM_QUERIES + " queries) ---");
        String[] queries = randomTerms(NUM_QUERIES, SEED + 1);
        System.out.printf("  %-20s  %8s  %8s  %s%n", "Corpus size", "Linear", "Index", "Speedup");

        for (int numDocs : CORPUS_SIZES) {
            List<Document> corpus = buildCorpus(numDocs, SEED);
            LinearScanner scanner = new LinearScanner(corpus);
            SearchIndex index = new SearchIndex();
            for (Document doc : corpus) index.add(doc);

            long start = System.nanoTime();
            for (String q : queries) scanner.search(q);
            long scanMs = msElapsed(start);

            start = System.nanoTime();
            for (String q : queries) index.search(q);
            long indexMs = msElapsed(start);

            System.out.printf("  %,6d docs          %4d ms    %4d ms   %s%n",
                    numDocs, scanMs, indexMs, speedup(scanMs, indexMs));
        }
    }

    static void benchmarkAndQuery() {
        System.out.println("--- AND Query: term1 AND term2 (" + NUM_QUERIES + " queries) ---");
        String[] terms1 = randomTerms(NUM_QUERIES, SEED + 2);
        String[] terms2 = randomTerms(NUM_QUERIES, SEED + 3);
        System.out.printf("  %-20s  %8s  %8s  %s%n", "Corpus size", "Linear", "Index", "Speedup");

        for (int numDocs : CORPUS_SIZES) {
            List<Document> corpus = buildCorpus(numDocs, SEED);
            LinearScanner scanner = new LinearScanner(corpus);
            SearchIndex index = new SearchIndex();
            for (Document doc : corpus) index.add(doc);

            long start = System.nanoTime();
            for (int i = 0; i < NUM_QUERIES; i++) scanner.searchAnd(terms1[i], terms2[i]);
            long scanMs = msElapsed(start);

            start = System.nanoTime();
            for (int i = 0; i < NUM_QUERIES; i++) index.searchAnd(terms1[i], terms2[i]);
            long indexMs = msElapsed(start);

            System.out.printf("  %,6d docs          %4d ms    %4d ms   %s%n",
                    numDocs, scanMs, indexMs, speedup(scanMs, indexMs));
        }
    }

    // -------------------------------------------------------------------------
    // Implementations (inner classes)
    // -------------------------------------------------------------------------

    /**
     * Brute-force: scans every document for every query.
     * O(N × W) per query where N = corpus size, W = words per document.
     */
    static class LinearScanner {
        private final List<Document> docs;

        LinearScanner(List<Document> docs) { this.docs = docs; }

        List<Integer> search(String term) {
            List<Integer> result = new ArrayList<>();
            for (Document doc : docs) {
                if (doc.words.contains(term)) result.add(doc.id);
            }
            return result;
        }

        List<Integer> searchAnd(String term1, String term2) {
            List<Integer> result = new ArrayList<>();
            for (Document doc : docs) {
                if (doc.words.contains(term1) && doc.words.contains(term2)) result.add(doc.id);
            }
            return result;
        }
    }

    /**
     * Inverted index: term → sorted posting list of document IDs.
     *
     * Build: O(N × W) one time.
     * Single-term query: O(1) hash lookup.
     * AND query: O(|p1| + |p2|) sorted-list merge (same algorithm as merge sort).
     */
    static class SearchIndex {
        private final Map<String, List<Integer>> index = new HashMap<>();

        void add(Document doc) {
            // Deduplicate terms per document before inserting into posting lists
            Set<String> seen = new HashSet<>(doc.words);
            for (String term : seen) {
                index.computeIfAbsent(term, k -> new ArrayList<>()).add(doc.id);
            }
            // Posting lists stay sorted because documents are added in ID order
        }

        List<Integer> search(String term) {
            return index.getOrDefault(term, Collections.emptyList());
        }

        /** Merges two sorted posting lists to find documents matching both terms. */
        List<Integer> searchAnd(String term1, String term2) {
            List<Integer> p1 = search(term1);
            List<Integer> p2 = search(term2);
            List<Integer> result = new ArrayList<>();
            int i = 0, j = 0;
            while (i < p1.size() && j < p2.size()) {
                int a = p1.get(i), b = p2.get(j);
                if      (a == b) { result.add(a); i++; j++; }
                else if (a <  b) { i++; }
                else             { j++; }
            }
            return result;
        }
    }

    static class Document {
        final int id;
        final List<String> words;

        Document(int id, List<String> words) { this.id = id; this.words = words; }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    static List<Document> buildCorpus(int numDocs, long seed) {
        Random rand = new Random(seed);
        List<Document> corpus = new ArrayList<>(numDocs);
        for (int id = 0; id < numDocs; id++) {
            List<String> words = new ArrayList<>(WORDS_PER_DOC);
            for (int w = 0; w < WORDS_PER_DOC; w++) words.add(VOCAB[rand.nextInt(VOCAB_SIZE)]);
            corpus.add(new Document(id, words));
        }
        return corpus;
    }

    static String[] randomTerms(int n, long seed) {
        Random rand = new Random(seed);
        String[] terms = new String[n];
        for (int i = 0; i < n; i++) terms[i] = VOCAB[rand.nextInt(VOCAB_SIZE)];
        return terms;
    }

    private static String speedup(long slowMs, long fastMs) {
        if (fastMs <= 0) return "(index <1 ms)";
        return String.format("%.0fx faster", (double) slowMs / fastMs);
    }

    private static long msElapsed(long startNs) {
        return (System.nanoTime() - startNs) / 1_000_000L;
    }
}
