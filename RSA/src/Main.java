import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        RSA rsa = new RSA();

        System.out.println("PRÍMEK GENERÁLÁSA:\n");

        //ket prim szam generalasa
        BigInteger p = rsa.generatePrime();
        System.out.println(p + ": " + p.isProbablePrime(1 ) + "\n");
        BigInteger q = rsa.generatePrime();

        while ( p.equals(q) ) {
            q = rsa.generatePrime();
            System.out.println("p = q");
        }
        System.out.println(q + ": " + q.isProbablePrime(1) + "\n");

        //n es phi(n) kiszamitasa
        BigInteger n = p.multiply(q);
        BigInteger phi_n = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        System.out.println("n: " + n);
        System.out.println("phi(n): " + phi_n + "\n");

        //phi(n)-hez relativ kicsi e szam
        BigInteger e = BigInteger.valueOf(3);
        System.out.println("(" + phi_n + ", " + e + "): " + rsa.Euclid_algorithm(phi_n, e)[0] + "\t" + phi_n.gcd(e));
        while ( rsa.Euclid_algorithm(phi_n, e)[0].compareTo(BigInteger.ONE) != 0 && e.compareTo(p) != 0 && e.compareTo(q) != 0) {
            e = e.add(BigInteger.TWO);
            System.out.println("(" + phi_n + ", " + e + "): " + rsa.Euclid_algorithm(phi_n, e)[0] + "\t" + phi_n.gcd(e));
        }

        if (e == p || e == q) {
            System.out.println("Nincs közös osztó!");
        }

        System.out.println("\ne: " + e);

        //e * d = 1 (mod phi_n)
        BigInteger d = rsa.Euclid_algorithm(phi_n, e)[1];   //NEMJÓ
        if (d.compareTo(BigInteger.ZERO) == -1) {
            d = d.add(phi_n);
        }
        //BigInteger d = rsa.searchForD(phi_n, e);
        System.out.println("d: " + d);
        System.out.println("e*d (mod phi(n))= " + ((e.multiply(d).abs()).mod(phi_n)));     //0-nak kell lennie, mert e*d | phi(n)+1 -et

        //titkosithato uzenetek halmaza: {0, ... , n-1 )
        Scanner reader = new Scanner(System.in);

        String in;

        do {
            System.out.print("\nTitkosítandó üzenet: ");
            in = reader.next();

            BigInteger msg = BigInteger.valueOf(Integer.parseInt(in));

            //encryption
            BigInteger crypted = rsa.QuickPow(msg, e, n);

            System.out.println("Titkosított üzenet: " + crypted);

            //decryption
            //BigInteger decrypted = rsa.QuickPow(crypted, d, n);
            //System.out.println("Visszafejtett üzenet: " + decrypted);
            BigInteger decrypted = rsa.Decrypt(p, q, crypted, d);
            System.out.println("Visszafejtett üzenet: " + decrypted);
        }
        while (Integer.parseInt(in) >= 0);

        /*
        //test euclid_algorithm
        BigInteger[] results = rsa.Euclid_algorithm(BigInteger.valueOf(2340), BigInteger.valueOf(113));
        System.out.println("LNKO: " + results[0]);
        System.out.println("d(y): " + results[1]);
        System.out.println("x: " + results[2]);

        //test quickpow
        System.out.println(rsa.QuickPow(BigInteger.valueOf(9), BigInteger.valueOf(67), BigInteger.valueOf(537)));
        System.out.println(BigInteger.valueOf(9).modPow(BigInteger.valueOf(67), BigInteger.valueOf(537)));

        //test decrypt
        System.out.println(rsa.Decrypt(BigInteger.valueOf(13), BigInteger.valueOf(5), BigInteger.valueOf(6), BigInteger.valueOf(19)));
         */
    }
}
