package com.chenhao.musicplayer.utils.crypt;

public final class KuwoDES {
	
	public static final byte[] SECRET_KEY = "ylzsxkwm".getBytes();
	public static final int SECRET_KEY_LENGTH = SECRET_KEY.length;

	private static final long g_arrayMask[] = { 0x0000000000000001l, 0x0000000000000002l, 0x0000000000000004l, 0x0000000000000008l, 0x0000000000000010l, 0x0000000000000020l, 0x0000000000000040l, 0x0000000000000080l, 0x0000000000000100l, 0x0000000000000200l, 0x0000000000000400l, 0x0000000000000800l, 0x0000000000001000l, 0x0000000000002000l, 0x0000000000004000l, 0x0000000000008000l, 0x0000000000010000l, 0x0000000000020000l, 0x0000000000040000l, 0x0000000000080000l, 0x0000000000100000l, 0x0000000000200000l, 0x0000000000400000l, 0x0000000000800000l, 0x0000000001000000l, 0x0000000002000000l, 0x0000000004000000l, 0x0000000008000000l, 0x0000000010000000l, 0x0000000020000000l, 0x0000000040000000l, 0x0000000080000000l, 0x0000000100000000l, 0x0000000200000000l, 0x0000000400000000l,
			0x0000000800000000l, 0x0000001000000000l, 0x0000002000000000l, 0x0000004000000000l, 0x0000008000000000l, 0x0000010000000000l, 0x0000020000000000l, 0x0000040000000000l, 0x0000080000000000l, 0x0000100000000000l, 0x0000200000000000l, 0x0000400000000000l, 0x0000800000000000l, 0x0001000000000000l, 0x0002000000000000l, 0x0004000000000000l, 0x0008000000000000l, 0x0010000000000000l, 0x0020000000000000l, 0x0040000000000000l, 0x0080000000000000l, 0x0100000000000000l, 0x0200000000000000l, 0x0400000000000000l, 0x0800000000000000l, 0x1000000000000000l, 0x2000000000000000l, 0x4000000000000000l, 0x8000000000000000l };

	private static final int g_arrayIP[] = { 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7, 56, 48, 40, 32, 24, 16, 8, 0, 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6 };

	private static final int g_arrayE[] = { 31, 0, 1, 2, 3, 4, -1, -1, 3, 4, 5, 6, 7, 8, -1, -1, 7, 8, 9, 10, 11, 12, -1, -1, 11, 12, 13, 14, 15, 16, -1, -1, 15, 16, 17, 18, 19, 20, -1, -1, 19, 20, 21, 22, 23, 24, -1, -1, 23, 24, 25, 26, 27, 28, -1, -1, 27, 28, 29, 30, 31, 30, -1, -1 };
	private static final char g_matrixNSBox[][] = { { 14, 4, 3, 15, 2, 13, 5, 3, 13, 14, 6, 9, 11, 2, 0, 5, 4, 1, 10, 12, 15, 6, 9, 10, 1, 8, 12, 7, 8, 11, 7, 0, 0, 15, 10, 5, 14, 4, 9, 10, 7, 8, 12, 3, 13, 1, 3, 6, 15, 12, 6, 11, 2, 9, 5, 0, 4, 2, 11, 14, 1, 7, 8, 13 }, { 15, 0, 9, 5, 6, 10, 12, 9, 8, 7, 2, 12, 3, 13, 5, 2, 1, 14, 7, 8, 11, 4, 0, 3, 14, 11, 13, 6, 4, 1, 10, 15, 3, 13, 12, 11, 15, 3, 6, 0, 4, 10, 1, 7, 8, 4, 11, 14, 13, 8, 0, 6, 2, 15, 9, 5, 7, 1, 10, 12, 14, 2, 5, 9 }, { 10, 13, 1, 11, 6, 8, 11, 5, 9, 4, 12, 2, 15, 3, 2, 14, 0, 6, 13, 1, 3, 15, 4, 10, 14, 9, 7, 12, 5, 0, 8, 7, 13, 1, 2, 4, 3, 6, 12, 11, 0, 13, 5, 14, 6, 8, 15, 2, 7, 10, 8, 15, 4, 9, 11, 5, 9, 0, 14, 3, 10, 7, 1, 12 },
			{ 7, 10, 1, 15, 0, 12, 11, 5, 14, 9, 8, 3, 9, 7, 4, 8, 13, 6, 2, 1, 6, 11, 12, 2, 3, 0, 5, 14, 10, 13, 15, 4, 13, 3, 4, 9, 6, 10, 1, 12, 11, 0, 2, 5, 0, 13, 14, 2, 8, 15, 7, 4, 15, 1, 10, 7, 5, 6, 12, 11, 3, 8, 9, 14 }, { 2, 4, 8, 15, 7, 10, 13, 6, 4, 1, 3, 12, 11, 7, 14, 0, 12, 2, 5, 9, 10, 13, 0, 3, 1, 11, 15, 5, 6, 8, 9, 14, 14, 11, 5, 6, 4, 1, 3, 10, 2, 12, 15, 0, 13, 2, 8, 5, 11, 8, 0, 15, 7, 14, 9, 4, 12, 7, 10, 9, 1, 13, 6, 3 }, { 12, 9, 0, 7, 9, 2, 14, 1, 10, 15, 3, 4, 6, 12, 5, 11, 1, 14, 13, 0, 2, 8, 7, 13, 15, 5, 4, 10, 8, 3, 11, 6, 10, 4, 6, 11, 7, 9, 0, 6, 4, 2, 13, 1, 9, 15, 3, 8, 15, 3, 1, 14, 12, 5, 11, 0, 2, 12, 14, 7, 5, 10, 8, 13 },
			{ 4, 1, 3, 10, 15, 12, 5, 0, 2, 11, 9, 6, 8, 7, 6, 9, 11, 4, 12, 15, 0, 3, 10, 5, 14, 13, 7, 8, 13, 14, 1, 2, 13, 6, 14, 9, 4, 1, 2, 14, 11, 13, 5, 0, 1, 10, 8, 3, 0, 11, 3, 5, 9, 4, 15, 2, 7, 8, 12, 15, 10, 7, 6, 12 }, { 13, 7, 10, 0, 6, 9, 5, 15, 8, 4, 3, 10, 11, 14, 12, 5, 2, 11, 9, 6, 15, 12, 0, 3, 4, 1, 14, 13, 1, 2, 7, 8, 1, 2, 12, 15, 10, 4, 0, 3, 13, 14, 6, 9, 7, 8, 9, 6, 15, 1, 5, 12, 3, 10, 14, 5, 8, 7, 11, 0, 4, 13, 2, 11 }, };

	private static final int g_arrayP[] = { 15, 6, 19, 20, 28, 11, 27, 16, 0, 14, 22, 25, 4, 17, 30, 9, 1, 7, 23, 13, 31, 26, 2, 8, 18, 12, 29, 5, 21, 10, 3, 24 };

	private static final int g_arrayIP_1[] = { 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25, 32, 0, 40, 8, 48, 16, 56, 24 };

	private static final int g_arrayPC_1[] = { 56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 60, 52, 44, 36, 28, 20, 12, 4, 27, 19, 11, 3 };

	private static final int g_arrayPC_2[] = { 13, 16, 10, 23, 0, 4, -1, -1, 2, 27, 14, 5, 20, 9, -1, -1, 22, 18, 11, 3, 25, 7, -1, -1, 15, 6, 26, 19, 12, 1, -1, -1, 40, 51, 30, 36, 46, 54, -1, -1, 29, 39, 50, 44, 32, 47, -1, -1, 43, 48, 38, 55, 33, 52, -1, -1, 45, 41, 49, 35, 28, 31, -1, -1 };

	private static final int g_arrayLs[] = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
	private static final long g_arrayLsMask[] = { 0x0000000000000000l, 0x0000000000100001l, 0x0000000000300003l };

	private static long BitTransform(int[] array, int len, long source) {
		long bts = source;
		int bti;
		long dest = 0;
		for (bti = 0; bti < len; bti++) {
			if (array[bti] >= 0 && (bts & g_arrayMask[array[bti]]) != 0) {
				dest |= g_arrayMask[bti];
			}
		}
		return dest;
	}

	private static final int DES_MODE_ENCRYPT = 0;
	private static final int DES_MODE_DECRYPT = 1;

	private static void DESSubKeys(long key, long[] K, int mode) {
		long temp = 0;
		int j;
		/* PC-1变换 */
		temp = BitTransform(g_arrayPC_1, 56, key);
		for (j = 0; j < 16; j++) {
			/* 循环左移 */
			{
				long source = temp;
				temp = ((source & g_arrayLsMask[g_arrayLs[j]]) << (28 - g_arrayLs[j])) | ((source & ~g_arrayLsMask[g_arrayLs[j]]) >> g_arrayLs[j]);
			}
			/* PC-2变换 */
			// 要初始化k的元素为0
			K[j] = BitTransform(g_arrayPC_2, 64, temp);
		}
		if (mode == DES_MODE_DECRYPT) /* 如果解密则反转子密钥顺序 */
		{
			long t;
			for (j = 0; j < 8; j++) {
				t = K[j];
				K[j] = K[15 - j];
				K[15 - j] = t;
			}
		}
	}

	private static long out = 0l;
	private static long L, R;
	private static int[] pSource = new int[2];
	private static byte[] pR = new byte[8];

	private static int SOut;
	private static int t;
	private static int sbi;
	public static final byte[] KSING_SECRET_KEY = "kwks&@69".getBytes();

	private static long DES64(long subkeys[], long data) {
		// private static int i;
		/* IP变换 */

		out = BitTransform(g_arrayIP, 64, data);

		pSource[0] = (int) (out & 0x00000000ffffffffl);
		pSource[1] = (int) ((out & 0xffffffff00000000l) >> 32);

		// R = new Long(pSource[1]);

		// pR[1] = (byte) ((0x00ff0000 & pSource[1]) >> 48);
		// pR[2] = (byte) ((0x0000ff00 & pSource[1]) >> 40);
		// pR[3] = (byte) (0x000000ff & pSource[1]>>32);
		// pR[0] = (byte) ((0xff000000 & pSource[1]) >> 24);
		// pR[1] = (byte) ((0x00ff0000 & pSource[1]) >> 16);
		// pR[2] = (byte) ((0x0000ff00 & pSource[1]) >> 8);
		// pR[3] = (byte) (0x000000ff & pSource[1]);

		// }

		/* 主迭代 */

		// source = out;
		for (int i = 0; i < 16; i++) {

			/* F变换开始 */

			R = pSource[1];

			/* E变换 */

			R = BitTransform(g_arrayE, 64, R);

			/* 与子密钥异或 */
			R ^= subkeys[i];
			/* S盒变换 */
			{

				for (int k = 0; k < 8; ++k) {
					pR[k] = (byte) (0xff & (R >> (k * 8)));
				}

				SOut = 0;
				for (sbi = 7; sbi >= 0; sbi--) {
					SOut <<= 4;

					SOut |= g_matrixNSBox[sbi][pR[sbi]];
				}
				R = SOut;
			}
			/* P变换 */

			R = BitTransform(g_arrayP, 32, R);

			/* f变换完成 */
			L = pSource[0];
			pSource[0] = pSource[1];
			pSource[1] = (int) (L ^ R);
		}
		/* 交换高低32位 */

		t = pSource[0];
		pSource[0] = pSource[1];
		pSource[1] = t;

		/* IP-1变换 */

		out = ((long) pSource[1] << 32 & 0xffffffff00000000l) | (long) (0x00000000ffffffffl & pSource[0]);
		out = BitTransform(g_arrayIP_1, 64, out);

		return out;
	}

	public synchronized static byte[] encrypt(byte[] src, int srcLength, byte[] key, int keyLength) {

		// long keyl = Long.valueOf(new String(key));
		long keyl = 0;
		for (int i = 0; i < 8; i++) {
			long temp = (long) key[i] << (i * 8);
			keyl |= temp;
		}

		int num = srcLength / 8;
		// byte[] szEncrypt = new byte[(num + 1) * 8 + 1];

		// 子密钥（临时数据）
		long[] subKey = new long[16];
		// for(Long tempKey:subKey){
		for (int i = 0; i < 16; i++) {
			subKey[i] = 0;
		}

		// 加密
		long[] pSrc = new long[num];
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < 8; j++) {
				pSrc[i] |= (long) src[i * 8 + j] << (j * 8);
			}
		}
		long[] pEncyrpt = new long[((num + 1) * 8 + 1) / 8];

		DESSubKeys(keyl, subKey, DES_MODE_ENCRYPT);

		for (int i = 0; i < num; i++) {
			// pEncyrpt[i] = DES64(subKey, (pSrc>>(56-i*8))&0xff);
			pEncyrpt[i] = DES64(subKey, pSrc[i]);
		}

		// 处理结尾处不够8个字节的部分
		int len = srcLength;
		int tail_num = len % 8;
		byte[] szTail = new byte[srcLength - num * 8];

		System.arraycopy(src, num * 8, szTail, 0, srcLength - num * 8);

		long tail64 = 0;
		for (int i = 0; i < tail_num; i++)
			tail64 = tail64 | ((long) (szTail[i])) << (i * 8);

		pEncyrpt[num] = DES64(subKey, tail64);

		// Base64编码
		// char szOut[1024]; //实验性质，需要多大空间需要计算，1024不一定够
		byte[] result = new byte[pEncyrpt.length * 8];
		int temp = 0;
		for (int i = 0; i < pEncyrpt.length; i++) {
			for (int j = 0; j < 8; ++j) {
				result[temp] = (byte) (0xff & (pEncyrpt[i] >> (j * 8)));
				temp++;
			}
		}

		//return new String(Base64Coder.encode(result, result.length, null));
		return result;
	}
	
	// 兼容汉字符
	public synchronized static byte[] encrypt2(byte[] src, int srcLength, byte[] key, int keyLength) {

		// long keyl = Long.valueOf(new String(key));
		long keyl = 0;
		for (int i = 0; i < 8; i++) {
			long temp = (long) key[i] << (i * 8);
			keyl |= temp;
		}

		int num = srcLength / 8;
		// byte[] szEncrypt = new byte[(num + 1) * 8 + 1];

		// 子密钥（临时数据）
		long[] subKey = new long[16];
		// for(Long tempKey:subKey){
		for (int i = 0; i < 16; i++) {
			subKey[i] = 0;
		}

		// 加密
		long[] pSrc = new long[num];
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < 8; j++) {
				pSrc[i] |= (long) (src[i * 8 + j]&0xff) << (j * 8);
			}
		}
		long[] pEncyrpt = new long[((num + 1) * 8 + 1) / 8];

		DESSubKeys(keyl, subKey, DES_MODE_ENCRYPT);

		for (int i = 0; i < num; i++) {
			// pEncyrpt[i] = DES64(subKey, (pSrc>>(56-i*8))&0xff);
			pEncyrpt[i] = DES64(subKey, pSrc[i]);
		}

		// 处理结尾处不够8个字节的部分
		int len = srcLength;
		int tail_num = len % 8;
		byte[] szTail = new byte[srcLength - num * 8];

		System.arraycopy(src, num * 8, szTail, 0, srcLength - num * 8);

		long tail64 = 0;
		for (int i = 0; i < tail_num; i++)
			tail64 = tail64 | ((long) (szTail[i]&0xff)) << (i * 8);

		pEncyrpt[num] = DES64(subKey, tail64);

		// Base64编码
		// char szOut[1024]; //实验性质，需要多大空间需要计算，1024不一定够
		byte[] result = new byte[pEncyrpt.length * 8];
		int temp = 0;
		for (int i = 0; i < pEncyrpt.length; i++) {
			for (int j = 0; j < 8; ++j) {
				result[temp] = (byte) (0xff & (pEncyrpt[i] >> (j * 8)));
				temp++;
			}
		}

		//return new String(Base64Coder.encode(result, result.length, null));
		return result;
	}

	// 兼容汉字符
	public synchronized static byte[] encryptKSing(byte[] src, byte[] key) {
		int srcLength = src.length;
		int keyLength = key.length;
		// 子密钥（临时数据）
		long[] subKey = new long[16];
		// for(Long tempKey:subKey){
		for (int i = 0; i < 16; i++) {
			subKey[i] = 0;
		}
		long keyl = 0;
		for (int i = 0; i < 8; i++) {
			long temp = (long) key[i] << (i * 8);
			keyl |= temp;
		}
		DESSubKeys(keyl, subKey, DES_MODE_ENCRYPT);
		// 加密
		int num = srcLength / 8;
		long[] pSrc = new long[num];
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < 8; j++) {
				pSrc[i] |= (long) (src[i * 8 + j]&0xff) << (j * 8);
			}
		}
		long[] pEncyrpt = new long[((num + 1) * 8 + 1) / 8];
		for (int i = 0; i < num; i++) {
			// pEncyrpt[i] = DES64(subKey, (pSrc>>(56-i*8))&0xff);
			pEncyrpt[i] = DES64(subKey, pSrc[i]);
		}
		// 处理结尾处不够8个字节的部分
		int len = srcLength;
		int tail_num = len % 8;
		byte[] szTail = new byte[srcLength - num * 8];
		System.arraycopy(src, num * 8, szTail, 0, srcLength - num * 8);
		long tail64 = 0;
		for (int i = 0; i < tail_num; i++)
			tail64 = tail64 | ((long) (szTail[i]&0xff)) << (i * 8);
		pEncyrpt[num] = DES64(subKey, tail64);
		// Base64编码
		// char szOut[1024]; //实验性质，需要多大空间需要计算，1024不一定够
		byte[] result = new byte[pEncyrpt.length * 8];
		int temp = 0;
		for (int i = 0; i < pEncyrpt.length; i++) {
			for (int j = 0; j < 8; ++j) {
				result[temp] = (byte) (0xff & (pEncyrpt[i] >> (j * 8)));
				temp++;
			}
		}
		return result;
	}

	/**
	 * @param src 加密数组，注：如果加密串已经进行base64，src是base64解码后的内容
	 * @param srcLength 对于解密来说，srcLength必须是8的倍数，这个需要上层来保证
	 * @param key
	 * @param keyLength
	 * @return 解密的结果，注：返回值尾部可能会有多余的0，需要上层决定有效数据的长度
	 */
	public synchronized static byte[] decryptKSing(byte[] src, byte[] key) { // 1. 计算密钥
		int srcLength = src.length;
		int keyLength = key.length;
		// 子密钥（临时数据）
		// 用于之后的解密过程
		long[] subKey = new long[16];
		long keyl = 0;
		for (int i = 0; i < 8; i++) {
			long temp = (long) key[i] << (i * 8);
			keyl |= temp;
		}
		for (int i = 0; i < 16; i++) {
			subKey[i] = 0;
		}
		DESSubKeys(keyl, subKey, DES_MODE_DECRYPT);
		// 2. des解密
		int num = srcLength / 8;
		long[] encryptData = new long[num];
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < 8; j++) {
				encryptData[i] |= (long) (src[i * 8 + j]&0xff) << (j * 8);
			}
		}
		long[] planeData = new long[num];
		for (int i = 0; i < num; i++) {
			planeData[i] = DES64(subKey, encryptData[i]);
		}
		byte[] result = new byte[num * 8];
		for (int i = 0; i < num; ++i) {
			for (int j = 0; j < 8; ++j) {
				result[i * 8 + j] = (byte)(0xff & (planeData[i] >> j * 8) );
			}
		}
		return result;
	}

}

