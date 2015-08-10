package textComponents;

import javax.swing.*;

/**
 * Model dynamicznie generujący permutacje słowa
 */
public class PermutationSpinnerModel extends AbstractSpinnerModel {
    private String word;

    public PermutationSpinnerModel(String w) {
        word = w;
    }

    public Object getValue() {
        return word;
    }

    @Override
    public void setValue(Object value) {
        if (!(value instanceof String)) throw new IllegalArgumentException();
        word = (String) value;
        fireStateChanged();
    }

    @Override
    public Object getNextValue() {
        int[] codePoints = toCodePointArray(word);

        for(int i = codePoints.length - 1; i > 0; i--) {
            if(codePoints[i - 1] < codePoints[i]) {
                int j = codePoints.length - 1;
                while(codePoints[i - 1] > codePoints[j])
                    j--;
                swap(codePoints, i - 1, j);
                reverse(codePoints, i, codePoints.length - 1);
                return new String(codePoints, 0, codePoints.length);
            }
        }
        reverse(codePoints, 0, codePoints.length - 1);
        return new String(codePoints, 0, codePoints.length);
    }

    @Override
    public Object getPreviousValue() {
        int[] codePoints = toCodePointArray(word);
        for(int i = codePoints.length - 1; i > 0; i--) {
            if(codePoints[i - 1] > codePoints[i]) {
                int j = codePoints.length - 1;
                while(codePoints[i - 1] < codePoints[j])
                    j--;
                swap(codePoints, i - 1, j);
                reverse(codePoints, i, codePoints.length - 1);
                return new String(codePoints, 0, codePoints.length);
            }
        }
        reverse(codePoints, 0, codePoints.length - 1);
        return new String(codePoints, 0 , codePoints.length);
    }

    private static int[] toCodePointArray(String string) {
        int[] codePoints = new int[string.codePointCount(0, string.length())];
        for (int i = 0, j = 0; i < string.length(); i++, j++) {
            int cp = string.codePointAt(i);
            if (Character.isSupplementaryCodePoint(cp)) i++;
            codePoints[j] = cp;
        }
        return codePoints;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static void reverse(int[] a, int i, int j) {
        while (i < j) {
            swap(a, i, j);
            i++;
            j--;
        }
    }
}
