import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.script.Bindings;
import java.math.BigInteger;
import java.security.KeyPair;
import java.util.*;

public class RSA {
    public BigInteger generatePrime() {
        Random rand = new Random();
        BigInteger p = new BigInteger(10, rand);

        if (p.mod(BigInteger.TWO) == BigInteger.ZERO) {     //ha páros, akkor +1
            p.add(BigInteger.ONE);
        }

        int i = 0;
        while ( !MillerRabinTest(p, 100) && i < 1000) {      //amig nem lesz prim 2-vel noveljuk
            p = p.add(BigInteger.TWO);
            i++;
            if (i == 1000) {
                p = new BigInteger(10, rand);
                i = 0;
            }
        }

        System.out.println(i + " teszt volt szükséges összesen");
        return p;
    }

    public static boolean MillerRabinTest(@NotNull BigInteger n, int numberOfTests) {
        if (n.compareTo(BigInteger.ONE) <= 0) { //ha n <= 1
            return false;       //nemprím
        }
        else if (n.compareTo(BigInteger.valueOf(3)) <= 0) {      // 1 < n <= 3
            return true;                 //prím
        }
        else if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {       //ha n paros -> nemprím
            return false;
        } else {
            for (int j = 0; j < numberOfTests; j++) {
                BigInteger a;
                do {
                    a = new BigInteger(n.bitLength() - 1, new Random());    //bitLength(): 2^hanyadikon ábrázolható a szám binárisan
                } while (a.equals(BigInteger.ZERO));    //megall, ha a!=0

                // gcd(n, a) ≠ 1
                if (!n.gcd(a).equals(BigInteger.ONE)) { //gcd=LNKO
                    return false;                       //ha nem relativ primek -> false
                }

                // d = (n-1) / (2^S)
                BigInteger d = n.subtract(BigInteger.ONE);  //d = n-1

                // S = max{ r | 2^r osztója (n-1)-nek }
                int S = 0;
                while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) { //mig d paros osztjuk 2-vel
                    d = d.divide(BigInteger.TWO);
                    S++;
                }

                // a^d ≡ 1 (mod n)
                if (a.modPow(d, n).equals(BigInteger.ONE)) {    //a^d mod n
                    return true;
                }

                // r:{0 , ... , S-1}: a^(d*2^r) ≡ -1 (mod n)
                for (int i = 0; i <= S - 1; i++) {
                    if (a.modPow(d.multiply(BigInteger.TWO.pow(i)), n).equals(BigInteger.ONE.negate())) {   //a^(d*2^i) mod n == -1
                        return true;
                    }
                }

            }
            return false;
        }
    }

    public static BigInteger[] Euclid_algorithm (@NotNull BigInteger a, BigInteger b) {
        BigInteger dividend = BigInteger.ONE;
        BigInteger divisor = BigInteger.ONE;

        if (a.compareTo(b) == 1) {     // a > b
            dividend = a;    //osztando
            divisor = b;     //oszto
        } else {        // a < b
            dividend = b;    //osztando
            divisor = a;     //oszto
        }

        BigInteger quotient = dividend.divide(divisor);      //hanyados
        BigInteger remainder = dividend.mod(divisor);     //maradek
        BigInteger x_dividend = BigInteger.ONE;
        BigInteger y_dividend = BigInteger.ZERO;
        BigInteger x_divisor = BigInteger.ZERO;
        BigInteger y_divisor = BigInteger.ONE;
        BigInteger x_newcol = x_divisor.multiply(quotient).add(x_dividend);
        BigInteger y_newcol = y_divisor.multiply(quotient).add(y_dividend);

        int counter = 2;

        while (remainder != BigInteger.ZERO) {
            dividend = divisor;    //osztando
            divisor = remainder;     //oszto
            quotient = dividend.divide(divisor);     //hanyados
            remainder = dividend.mod(divisor);     //maradek
            x_dividend = x_divisor;
            y_dividend = y_divisor;
            x_divisor = x_newcol;
            y_divisor = y_newcol;
            x_newcol = x_divisor.multiply(quotient).add(x_dividend);
            y_newcol = y_divisor.multiply(quotient).add(y_dividend);

            counter++;
        }

        //x = x_divisor, y = y_divisor
        BigInteger[] result = {divisor, y_divisor, x_divisor, BigInteger.valueOf(counter)};
        return result;
    }

    public static BigInteger QuickPow(@NotNull BigInteger base, @NotNull BigInteger pow, BigInteger modulo) {
        BigInteger remainder_class = pow;
        Vector<Integer> mods = new Vector<>();
        for (int i = pow.bitLength()-1; i >= 0; i--) {
            if (BigInteger.TWO.pow(i).compareTo(remainder_class) <= 0) {     //ha 2^i < remainder
                mods.add(i);
                remainder_class = remainder_class.subtract(BigInteger.TWO.pow(i));
            }
        }

        BigInteger modsmultiply = BigInteger.ONE;
        if (mods.lastElement() == 0) {
            mods.remove(mods.size() - 1);
            modsmultiply = modsmultiply.multiply(base.modPow(BigInteger.TWO.pow(0), modulo));
        }
        remainder_class = base.modPow(BigInteger.TWO.pow(0), modulo);

        for (int i=1; i < pow.bitLength(); i++) {
            remainder_class = remainder_class.modPow(BigInteger.TWO, modulo);

            if (mods.lastElement() == i) {
                mods.remove(mods.size() - 1);
                modsmultiply = modsmultiply.multiply(remainder_class);
            }
        }

        return modsmultiply.mod(modulo);
    }

    public static BigInteger Decrypt(@NotNull BigInteger p, @NotNull BigInteger q, BigInteger crypted, @NotNull BigInteger d) {
        BigInteger c1 = QuickPow(crypted, d.mod(p.subtract(BigInteger.ONE)), p);
        BigInteger c2 = QuickPow(crypted, d.mod(q.subtract(BigInteger.ONE)), q);

        BigInteger M = p.multiply(q);
        BigInteger M1 = q;
        BigInteger M2 = p;

        BigInteger y1 = BigInteger.ONE.negate().pow(Euclid_algorithm(p, q)[3].intValue() - 1).multiply(Euclid_algorithm(p, q)[2]);  // (-1)^counter * x_divisor
        BigInteger y2 = BigInteger.ONE.negate().pow(Euclid_algorithm(p, q)[3].intValue()).multiply(Euclid_algorithm(p, q)[1]);

        if (y1.multiply(M1).mod(p).compareTo(BigInteger.ONE) != 0) {    //y1*M1 = 1 (mod p)
            BigInteger h = y1;
            y1 = y2;
            y2 = y1;
        }

        BigInteger decrypted = (c1.multiply(y1).multiply(M1).add(c2.multiply(y2).multiply(M2))).mod(M);
        return decrypted;
    }
}