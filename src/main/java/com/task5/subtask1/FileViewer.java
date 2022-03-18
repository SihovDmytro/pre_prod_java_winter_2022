package com.task5.subtask1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileViewer implements Iterable<String> {
    private File file;
    private static final Logger LOG = LogManager.getLogger(FileViewer.class);

    public FileViewer(String path) {
        file = new File(path);
    }

    @Override
    public Iterator<String> iterator() {
        return new FileIterator();
    }

    public boolean exists() {
        return file.exists();
    }

    class FileIterator implements Iterator<String> {
        private Scanner scanner;

        public FileIterator() {
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException exception) {
                scanner = null;
                LOG.info("Cannot find specified file", exception);
            }
        }

        @Override
        public boolean hasNext() {
            boolean check = scanner != null && scanner.hasNextLine();
            if (!check) {
                closeScanner();
            }
            return check;
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return scanner.nextLine();
        }

        private void closeScanner() {
            try {
                scanner.close();
            } catch (NullPointerException | IllegalStateException exception) {
                LOG.warn("Cannot close scanner", exception);
            }
        }
    }

}
