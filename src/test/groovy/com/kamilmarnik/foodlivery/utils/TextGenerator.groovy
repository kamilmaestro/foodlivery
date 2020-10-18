package com.kamilmarnik.foodlivery.utils

class TextGenerator {

  static String randomText(int length) {
    String letters = (('A'..'Z') + ('0'..'9')).join()
    return generateTextFor(letters, length)
  }

  private static String generateTextFor(String letters, int length) {
    return new Random().with {
      (1..length).collect { letters[nextInt(letters.length())] }.join()
    }
  }

}
