class ThresholdCalculator {
    int ratio(int size) {
        int _1B = 1;
        int _1KB = _1B * 1024;
        int _100KB = _1KB * 100;
        int _500KB = _1KB * 500;
        int _1MB = _1KB * 1024;

        if (size > _1MB) {
            //放大访问次数系数
            return 100;
        }
        if (size > _500KB) {
            //放大访问次数系数
            return 5;
        }
        if (size > _100KB) {
            //放大访问次数系数
            return 2;
        }
        return 1;
    }
}