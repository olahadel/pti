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
        BigInteger e = BigInteger.valueOf(2);
        System.out.println("(" + phi_n + ", " + e + "): " + rsa.Euclid_algorithm(phi_n, e)[0] + "\t" + phi_n.gcd(e));
        while ( rsa.Euclid_algorithm(phi_n, e)[0].compareTo(BigInteger.ONE) != 0 && e.compareTo(p) != 0 && e.compareTo(q) != 0) {
            if (phi_n.mod(BigInteger.TWO) == BigInteger.ZERO) { //ha phi_n páros
                e = e.add(BigInteger.ONE);
            }
            else {
                e = e.add(BigInteger.TWO);
            }

            System.out.println("(" + phi_n + ", " + e + "): " + rsa.Euclid_algorithm(phi_n, e)[0] + "\t" + phi_n.gcd(e));
        }

        if (e == p || e == q) {
            System.out.println("Nincs közös osztó!");
        }

        System.out.println("\ne: " + e);

        //e * d = 1 (mod phi_n)
        BigInteger d = rsa.Euclid_algorithm(phi_n, e)[1];
        System.out.println("d: " + d);
        //BigInteger PublicKey = rsa.Euclid_algorithm(e, n)[0];
        //BigInteger SecretKey =  rsa.Euclid_algorithm(d, n)[0];
        //System.out.println("\nPK: " + PublicKey + "\t" + e.gcd(n));
        //System.out.println("SK: " + SecretKey + "\t" + d.gcd(n));

        //titkosithato uzenetek halmaza: {0, ... , n-1 )
        Scanner reader = new Scanner(System.in);

        String in;

        do {
            System.out.print("\nTitkosítandó üzenet: ");
            in = reader.next();

            BigInteger msg = BigInteger.valueOf(Integer.parseInt(in));

            //encryption
            BigInteger crypted = rsa.QuickPow(msg, e, n);

            //decryption
            BigInteger decrypted = rsa.Decrypt(p, q, crypted, d);

            System.out.println("Titkosított üzenet: " + crypted);
            System.out.println("Visszafejtett üzenet: " + decrypted);
        }
        while (Integer.parseInt(in) > 0);

        /*
        //test euclid_algorithm
        System.out.println("(160, 19): " + rsa.Euclid_algorithm(BigInteger.valueOf(534), BigInteger.valueOf(141))[0]);
        System.out.println("d: " + rsa.Euclid_algorithm(BigInteger.valueOf(534), BigInteger.valueOf(141))[1]);

        //test quickpow
        System.out.println(rsa.QuickPow(BigInteger.valueOf(9), BigInteger.valueOf(67), BigInteger.valueOf(537)));
        System.out.println(BigInteger.valueOf(9).modPow(BigInteger.valueOf(67), BigInteger.valueOf(537)));
         */
    }
}
