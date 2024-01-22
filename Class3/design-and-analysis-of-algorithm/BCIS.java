
/**
 *
 * @author GROUP8
 */
public class BCIS {

    public int isEqual(int[] array, int SL, int SR) {
        for (int k = SL + 1; k <= SR - 1; k++) {
            if (array[k] != array[SL]) {
                swap(array, k, SL);
                return k;
            }
        }
        return -1;
    }

    public void insRight(int[] array, int currItem, int SR, int right) {
        int j = SR;
        while (j <= right && currItem > array[j]) {
            array[j - 1] = array[j];
            j = j + 1;
        }
        array[j - 1] = currItem;
    }

    public void insLeft(int[] array, int currItem, int SL, int left) {
        int j = SL;
        while (j >= left && currItem < array[j]) {
            array[j + 1] = array[j];
            j = j - 1;
        }
        array[j + 1] = currItem;
    }

    public void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public void BCIS(int[] array, int left, int right) {
        int SL = left;
        int SR = right;
        int i;
        while (SL < SR) {
            swap(array, SR, SL + ((SR - SL) / 2));
            if (array[SL] == array[SR]) {
                if (isEqual(array, SL, SR) == -1) {
                    return;
                }
            }
            if (array[SL] > array[SR]) {
                swap(array, SL, SR);
            }
            if ((SR - SL) >= 100) {
                for (i = SL + 1; i <= Math.pow(SR - SL, 0.5); i++) {
                    if (array[SR] < array[i]) {
                        swap(array, SR, i);
                    } else if (array[SL] > array[i]) {
                        swap(array, SL, i);
                    }
                }
            } else {
                i = SL + 1;
            }
            //current left
            int LC = array[SL];
            int RC = array[SR];
            while (i < SR) {
                int currItem = array[i];
                if (currItem > RC || currItem == RC) {
                    array[i] = array[SR - 1];
                    insRight(array, currItem, SR, right);
                    SR = SR - 1;
                } else if (currItem < LC || currItem == LC) {
                    array[i] = array[SL + 1];
                    insLeft(array, currItem, SL, left);
                    SL = SL + 1;
                    i = i + 1;
                } else {
                    i = i + 1;
                }
            }
            SL = SL + 1;
            SR = SR - 1;
        }
    }

    public static void main(String[] args) {
        BCIS e = new BCIS();
        int[] array = {22,100000, 17, 56, 57, 52, 59, 80, 78, 73, 19, 53, 28, 65, 72, 67,100000};

        System.out.println("Unsorted Array:");
        for (Integer array1 : array) {
            System.out.print(array1 + " ");
        }
        e.BCIS(array, 0, array.length - 1);
        System.out.println("\nSorted Array:");
        for (Integer array1 : array) {
            System.out.print(array1 + " ");
        }

    }
}
