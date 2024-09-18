import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.crypto.interfaces.*;
   /*
    * This program executes the Diffie-Hellman key agreement protocol between
    * 4 parties: Alice, Bob, Carol and Kyle using a shared 2048-bit DH parameter.
    */
    public class DHKeyAgreement4 {
        private DHKeyAgreement4() {}
        public static void main(String argv[]) throws Exception {


        // Alice creates her own DH key pair with 2048-bit key size
            KeyPairGenerator aliceKpairGen = KeyPairGenerator.getInstance("DH");
            aliceKpairGen.initialize(2048);
            KeyPair aliceKpair = aliceKpairGen.generateKeyPair();

        // This DH parameters can also be constructed by creating a
        // DHParameterSpec object using agreed-upon values
            DHParameterSpec dhParamShared = ((DHPublicKey)aliceKpair.getPublic()).getParams();

        // Bob creates his own DH key pair using the same params
            KeyPairGenerator bobKpairGen = KeyPairGenerator.getInstance("DH");
            bobKpairGen.initialize(dhParamShared);
            KeyPair bobKpair = bobKpairGen.generateKeyPair();

        // Carol creates her own DH key pair using the same params
            KeyPairGenerator carolKpairGen = KeyPairGenerator.getInstance("DH");
            carolKpairGen.initialize(dhParamShared);
            KeyPair carolKpair = carolKpairGen.generateKeyPair();

        // Kyle creates his own DH key pair using the same params
            KeyPairGenerator kyleKpairGen = KeyPairGenerator.getInstance("DH");
            kyleKpairGen.initialize(dhParamShared);
            KeyPair kyleKpair = kyleKpairGen.generateKeyPair();




          //Alice initialize
          KeyAgreement aliceKeyAgree = KeyAgreement.getInstance("DH");
          aliceKeyAgree.init(aliceKpair.getPrivate());

          //Bob initialize
          KeyAgreement bobKeyAgree = KeyAgreement.getInstance("DH");
          bobKeyAgree.init(bobKpair.getPrivate());

          //Carol initialize
          KeyAgreement carolKeyAgree = KeyAgreement.getInstance("DH");
          carolKeyAgree.init(carolKpair.getPrivate());

          //Kyle initialize
          KeyAgreement kyleKeyAgree = KeyAgreement.getInstance("DH");
          kyleKeyAgree.init(kyleKpair.getPrivate());


          //First Pass

          //Alice computes gKA
          Key gKA = aliceKeyAgree.doPhase(kyleKpair.getPublic(), false);

          //Bob computes gAB
          Key gAB = bobKeyAgree.doPhase(aliceKpair.getPublic(), false); 

          //Carol computes gBC
          Key gBC = carolKeyAgree.doPhase(bobKpair.getPublic(), false); 

          //Kyle computes gCK
          Key gCK = kyleKeyAgree.doPhase(carolKpair.getPublic(), false);


          //Second Pass

          //Alice computes gCKA
          Key gCKA = aliceKeyAgree.doPhase(gCK, false);

          //Bob computes gKAB
          Key gKAB = bobKeyAgree.doPhase(gKA, false);

          //Carol computes gABC
          Key gABC = carolKeyAgree.doPhase(gAB, false);

          //Kyle computes gBCK
          Key gBCK = kyleKeyAgree.doPhase(gBC, false); 


          //Third Pass

          //Alice computes gBCKA
          Key gBCKA = aliceKeyAgree.doPhase(gBCK, true); 

          //Bob computes gCKAB
          Key gCKAB = bobKeyAgree.doPhase(gCKA, true); 

          //Kyle Computes gABCK
          Key gABCK = kyleKeyAgree.doPhase(gABC, true); 

          //Carol computes gKABC
          Key gKABC = carolKeyAgree.doPhase(gKAB, true); 



        // Alice, Bob, Carol and Kyle compute their secrets
            byte[] aliceSharedSecret = aliceKeyAgree.generateSecret();
            System.out.println("Alice's secret: " + toHexString(aliceSharedSecret));

            byte[] bobSharedSecret = bobKeyAgree.generateSecret();
            System.out.println("Bob's secret: " + toHexString(bobSharedSecret));

            byte[] carolSharedSecret = carolKeyAgree.generateSecret();
            System.out.println("Carol's secret: " + toHexString(carolSharedSecret));

            byte[] kyleSharedSecret = kyleKeyAgree.generateSecret();
            System.out.println("Kyle's secret: " + toHexString(kyleSharedSecret));

        // Compare Alice and Bob
            if (!java.util.Arrays.equals(aliceSharedSecret, bobSharedSecret))
                System.out.println("Alice and Bob differ");
            else
                System.out.println("Alice and Bob are the same");

        // Compare Bob and Carol
            if (!java.util.Arrays.equals(bobSharedSecret, carolSharedSecret))
                System.out.println("Bob and Carol differ");
            else
                System.out.println("Bob and Carol are the same");

          //Compare Carol and Kyle
            if (!java.util.Arrays.equals(carolSharedSecret, kyleSharedSecret))
                System.out.println("Carol and Kyle differ");
            else
                System.out.println("Carol and Kyle are the same");

        }
    /*
     * Converts a byte to hex digit and writes to the supplied buffer
     */
        private static void byte2hex(byte b, StringBuffer buf) {
            char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                                '9', 'A', 'B', 'C', 'D', 'E', 'F' };
            int high = ((b & 0xf0) >> 4);
            int low = (b & 0x0f);
            buf.append(hexChars[high]);
            buf.append(hexChars[low]);
        }
    /*
     * Converts a byte array to hex string
     */
        private static String toHexString(byte[] block) {
            StringBuffer buf = new StringBuffer();
            int len = block.length;
            for (int i = 0; i < len; i++) {
                byte2hex(block[i], buf);
                if (i < len-1) {
                    buf.append(":");
                }
            }
            return buf.toString();
        }
    }
