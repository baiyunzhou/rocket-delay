package com.zby.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DelayArrayGenerator {

	private DelayArrayGenerator() {
	}

	public static long[] generateWithSize(int size) {
		long[] arr = new long[size];
		arr[0] = 1;
		for (int i = 0; i < size - 1; i++) {
			arr[i + 1] = arr[i] * 2;
		}
		return arr;
	}

	public static long[] generateWithMax(long max) {
		List<Long> list = new ArrayList<>();
		list.add(1L);
		long cur = 1;
		do {
			list.add(cur << 1);
			cur = list.get(list.size() - 1);
		} while (cur < max);
		long[] arr = new long[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}

	public static void main(String[] args) {
		System.out.println(7 * 24 * 60 * 60);
		System.out.println(Arrays.toString(generateWithSize(18)));
		System.out.println(Arrays.toString(generateWithMax(1 * 24 * 60 * 60)));
		System.out.println(Arrays.toString(generateWithSize(21)));
		System.out.println(Arrays.toString(generateWithMax(7 * 24 * 60 * 60)));
	}

}
