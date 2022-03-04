package com.task5.subtask1;

import com.task5.subtask1.FileViewer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

class FileViewerTest {
    private FileViewer viewer;
    private static final String FILE_PATH = "src/test/java/com/task5/subtask1/textFile.txt";

    @Test
    public void shouldReadFileLineByLine()
    {
        viewer = new FileViewer(FILE_PATH);
        Iterator<String> iterator = viewer.iterator();

        boolean check = iterator.next().equals("first line")
                && iterator.next().equals("second line")
                && iterator.next().equals(";adm nflan a.sfd d")
                && !iterator.hasNext();

        Assertions.assertTrue(check);
    }

    @Test
    public void shouldThrowNoSuchElementExceptionWhenNextMethodCalled()
    {
        viewer = new FileViewer("d;a,f;lf,;we");
        Iterator<String> iterator = viewer.iterator();

        Assertions.assertThrows(NoSuchElementException.class, iterator::next);
    }
}