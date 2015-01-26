package chinapay;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

import chinapay.Des;
import chinapay.DesKey;
import chinapay.PrivateKey;

/**
 * This class is copied from UnionPay jars to hack around the impl
 * @author blshao
 *
 */
public class UnionPayUtil {

	public static boolean buildKey(PrivateKey key, String MerID, int KeyUsage,
			String KeyFile) {
		byte[] iv = new byte[8];
		DesKey ks = new DesKey("SCUBEPGW".getBytes(), false);
		Des des = new Des(ks);
		BufferedReader br;
		FileInputStream fis;
		String MerPG_flag;
		String tmpString;
		byte[] KeyBuf = new byte[712];

		try {
			fis = new FileInputStream(KeyFile);
			br = new BufferedReader(new InputStreamReader(fis));
		} catch (FileNotFoundException _ex) {
			System.out.println("文件未找到:"+ _ex.getMessage());
			return false;
		}

		try {
			tmpString = br.readLine();
			if ((tmpString.compareTo("[SecureLink]") != 0)
					&& (tmpString.compareTo("[NetPayClient]") != 0)) {
				System.out.println("文件头错误,tmpString="+tmpString);
				boolean flag = false;
				boolean flag3 = flag;
				return flag3;
			}
			tmpString = br.readLine();
			int m_Pos = tmpString.indexOf("=");
			MerPG_flag = tmpString.substring(0, m_Pos);
			tmpString = tmpString.substring(m_Pos + 1, tmpString.length());

			if (tmpString.compareTo(MerID) != 0) {
				System.out.println("没找到商户id:"+tmpString);
				boolean flag1 = false;
				boolean flag4 = flag1;
				return flag4;
			}
			if (KeyUsage == 0) {
				tmpString = br.readLine();
				System.out.println("KeyUsage==0,tmpString="+tmpString);
				if (MerPG_flag.compareTo("PGID") != 0) {
					tmpString = tmpString.substring(88, tmpString.length());
				} else {
					tmpString = tmpString.substring(56, tmpString.length());
				}
			} else {
				System.out.println("KeyUsage!=0,tmpString="+tmpString);
				tmpString = br.readLine();
				System.out.println("readline,tmpString="+tmpString);
				tmpString = br.readLine();
				System.out.println("readline,tmpString="+tmpString);
				if (MerPG_flag.compareTo("PGID") != 0)
					tmpString = tmpString.substring(88, tmpString.length());
				else
					tmpString = tmpString.substring(56, tmpString.length());
			}
		} catch (Exception IE) {
			System.out.println("IOException:"+IE.getMessage());
			return false;
		} finally {
			try {
				if (br != null)
					br.close();
				if (fis != null)
					fis.close();
			} catch (IOException _ex) {
				return false;
			}
		}

		BigInteger Convert = new BigInteger(tmpString, 16);
		KeyBuf = Convert.toByteArray();
		if (KeyBuf[0] == 0) {
			if (MerPG_flag.compareTo("PGID") != 0) {
				for (int i = 0; i < 712; i++)
					KeyBuf[i] = KeyBuf[(i + 1)];
			} else {
				for (int i = 0; i < 264; i++)
					KeyBuf[i] = KeyBuf[(i + 1)];
			}
		}
		System.arraycopy(KeyBuf, 0, key.Modulus, 0, 128);
		if (MerPG_flag.compareTo("MERID") == 0) {
			memset(iv, (byte) 0, iv.length);
			des.cbc_encrypt(KeyBuf, 384, 64, key.Prime[0], 0, iv, false);
			memset(iv, (byte) 0, iv.length);
			des.cbc_encrypt(KeyBuf, 448, 64, key.Prime[1], 0, iv, false);
			memset(iv, (byte) 0, iv.length);
			des.cbc_encrypt(KeyBuf, 512, 64, key.PrimeExponent[0], 0, iv, false);
			memset(iv, (byte) 0, iv.length);
			des.cbc_encrypt(KeyBuf, 576, 64, key.PrimeExponent[1], 0, iv, false);
			memset(iv, (byte) 0, iv.length);
			des.cbc_encrypt(KeyBuf, 640, 64, key.Coefficient, 0, iv, false);
		} else if (MerPG_flag.compareTo("PGID") != 0) {
			System.out.println("MerPG_flag!=0,MerPG_flag="+MerPG_flag);
			return false;
		}
		return true;
	}

	private static void memset(byte[] out, byte c, int len) {
		for (int i = 0; i < len; i++)
			out[i] = c;
	}
}
