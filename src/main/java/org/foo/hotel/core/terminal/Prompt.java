package org.foo.hotel.core.terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Prompt {
    public static String read(String prompt, String ... aboardIfs) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(prompt);
        try {
            String line = reader.readLine();
            for (String aboardIf : aboardIfs)
                if (aboardIf.equals(line)) {
                    throw new AboardException();
                }
            return line;
        } catch (Exception e) {
        }
        throw new AboardException();
    }

    public static Integer readPositiveInt(String prompt, String aboardIf) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(prompt);
        try {
            String line = reader.readLine();
            if (!aboardIf.equals(line)) {
                int i = Integer.parseInt(line);
                return i > 0 ? i : null;
            }
        } catch (Exception e) {
        }
        throw new AboardException();
    }
}
