class Encoder {

  private char offset;

  private String ref = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()\\*+,-./";

  private String[] encodeTable;
  private String[] decodeTable;

  public Encoder(char offset) {
    this.offset = offset;
    encodeTable = buildEncodeTable(offset);
    decodeTable = buildDecodeTable(offset);
  }

  private String[] buildEncodeTable(char offset) {
    String[] table = new String[ref.length()];
    for (int i = 0; i < ref.length(); i++) {
      char c = ref.charAt(i);
      int index = (i + (offset - 'A')) % ref.length();
      table[index] = "" + c;
    }
    return table;
  }

  private String[] buildDecodeTable(char offset) {
    String[] table = new String[ref.length()];
    for (int i = 0; i < ref.length(); i++) {
      char c = ref.charAt(i);
      int index = (i - (offset - 'A') + ref.length()) % ref.length();
      table[index] = "" + c;
    }
    return table;
  }

  public String encode(String plainText) {
    StringBuilder encoded = new StringBuilder();
    encoded.append(offset);
    for (char c : plainText.toCharArray()) {
      if (ref.indexOf(c) != -1) {
        encoded.append(encodeTable[ref.indexOf(c)]);
      } else {
        encoded.append(c);
      }
    }
    return encoded.toString();
  }

  public String decode(String encodedText) {
    StringBuilder decoded = new StringBuilder();
    for (int i = 1; i < encodedText.length(); i++) {
      char c = encodedText.charAt(i);
      if (ref.indexOf(c) != -1) {
        decoded.append(decodeTable[ref.indexOf(c)]);
      } else {
        decoded.append(c);
      }
    }
    return decoded.toString();
  }

}

class Main{
public static void main(String[] args) {
  Encoder encoder = new Encoder('F'); 

  String encoded = encoder.encode("HELLO WORLD");
  System.out.println(encoded); // FC/GGJ RJMG.

  String decoded = encoder.decode(encoded);
  System.out.println(decoded); // HELLO WORLD
}
}
