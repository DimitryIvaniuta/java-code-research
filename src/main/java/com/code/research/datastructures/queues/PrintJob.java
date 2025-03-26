package com.code.research.datastructures.queues;

import lombok.Getter;

/**
 * Represents a print job in a printer queue system.
 */
@Getter
public class PrintJob {

    private final int jobId;
    private final String documentName;
    private final int totalPages;

    /**
     * Constructs a new PrintJob.
     *
     * @param jobId         the unique identifier for the print job
     * @param documentName  the name of the document to be printed
     * @param totalPages    the number of pages in the document
     */
    public PrintJob(int jobId, String documentName, int totalPages) {
        this.jobId = jobId;
        this.documentName = documentName;
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "PrintJob{" +
                "jobId=" + jobId +
                ", documentName='" + documentName + '\'' +
                ", totalPages=" + totalPages +
                '}';
    }

}