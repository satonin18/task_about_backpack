package com.app;

import java.io.*;

public class HelloWorld {

    public static void main(String[] args) throws IOException {
		System.out.println("input name please ");

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String name = bufferedReader.readLine();
		System.out.println("Hello "+ name);
		System.out.println("Bye "+ name);
    }
}
