class Solution {

    public List<Integer> powerfulIntegers(int x, int y, int bound) {

        HashSet<Integer> value = new HashSet<>();
        int min = (int) (Math.pow(x, 0) + Math.pow(y, 0));
        int max = bound;
        int max_pow_x = 0;
        int max_pow_y = 0;
        while (bound > (int) Math.pow(x, max_pow_x)) {
            if (x == 1) {
                max_pow_x = 1;
                break;
            }
            max_pow_x++;
        }
        while (bound > (int) Math.pow(y, max_pow_y)) {
            if (y == 1) {
                max_pow_y = 1;
                break;
            }
            max_pow_y++;
        }
        for (int i = 0; i < max_pow_x; i++) {
            for (int j = 0; j < max_pow_y; j++) {
                int num = (int) (Math.pow(x, i) + Math.pow(y, j));
                if (num <= bound) {
                    if (!value.contains(num)) {
                        value.add(num);
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>(value);
        return result;
    }
}