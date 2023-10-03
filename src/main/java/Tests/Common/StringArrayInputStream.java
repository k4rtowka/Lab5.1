package Tests.Common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

public class StringArrayInputStream extends InputStream {
    private Queue<byte[]> stringsQueue = new LinkedList<>();
    private int currentBytePos = 0;
    private byte[] currentByteArray = new byte[0];

    public StringArrayInputStream(String[] lines) {
        for (String line : lines) {
            // Добавляем строку и символ новой строки для каждой строки
            stringsQueue.add((line + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
        }
    }

    @Override
    public int read() throws IOException {
        if (currentBytePos >= currentByteArray.length) {
            currentByteArray = stringsQueue.poll(); // Получить следующий массив байтов из очереди
            if (currentByteArray == null) {
                return -1; // Если нет данных, возвращаем -1
            }
            currentBytePos = 0;
        }

        return currentByteArray[currentBytePos++];
    }
}

