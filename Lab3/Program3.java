public class Program3 implements IProgram3 {

    private int numClasses;
    private int maxGrade;
    GradeFunction gf;

    public Program3() {
        this.numClasses = 0;
        this.maxGrade = 0;
        this.gf = null;
    }

    public void initialize(int n, int g, GradeFunction gf) {
        this.numClasses = n;
        this.maxGrade = g;
        this.gf = gf;
    }

    public int[] computeHours(int totalHours) {
        Pair[][] opt = this.computeOPT(this.numClasses, totalHours, this.gf);
        int[] computeHours = new int[numClasses];

        int width = opt.length;
        int height = opt[0].length;

        // for (int i = 0; i < width; i ++) {
        //     for (int h = 0; h < height; h ++) {
        //         System.out.println("[" + i + ", " + h + "] = " + "{" + opt[i][h].optGrade + ", " + opt[i][h].numHours + "}");
        //     }
        // }

        int remainder = height;
        for (int i = width - 1; i >= 0; i --) {
            int maxGrade = -1; // the greatest grade achieved in row i
            int useHours = -1; // the number of hours used to achieve the max grade
            for (int h = 0; h < remainder; h ++) {
                int newGrade = opt[i][h].optGrade;
                if (newGrade > maxGrade) {
                    maxGrade = newGrade;
                    useHours = opt[i][h].numHours;
                }
            }
            remainder -= useHours;
            computeHours[i] = useHours;
        }

        return computeHours;
    }

    public int[] computeGrades(int totalHours) {
        int[] optHours = computeHours(totalHours);
        int[] computeGrades = new int[numClasses];

        for (int i = 0; i < optHours.length; i ++) {
            computeGrades[i] = this.gf.grade(i, optHours[i]);
        }

        return computeGrades;

    }

    /* Description: computes the optimal solution for all numClasses classes and totalHours hours using grade function gj
     * Output: returns an numClasses x totalHours matrix of tuples;  value [i, h] is the tuple of (optimal grade earned
     * for class i using at most h hours, the precise number of hours used to achieve the optimal grade)
     */
    private Pair[][] computeOPT(int numClasses, int totalHours, GradeFunction gf) {
        int totalHoursIncl = totalHours + 1; // opt is inclusive on totalHours
        Pair[][] opt = new Pair[numClasses][totalHoursIncl];

        // invalid input, so just return the empty matrix
        if (numClasses < 1) {
            return opt;
        }

        /* initialize the opt solution by filling in the first row */

        // first, fill in the (0,0) value which is used subsequently
        int grade = gf.grade(0, 0);
        opt[0][0] = new Pair(grade, 0);

        // fill in the rest of the first row
        for (int i = 1; i < numClasses; i ++) {
            grade = gf.grade(i, 0) + opt[i - 1][0].optGrade;
            opt[i][0] = new Pair(grade, 0);
        }

        /* comput the rest of the opt solution by iterating over all i in numClasses and h in totalHours */
        for (int i = 0; i < numClasses; i ++) {
            // System.out.println("i: " + i);
            for (int h = 0; h < totalHoursIncl; h ++) {
                // System.out.println("\th: " + h);
                int maxGrade = -1;
                int kHours = -1;

                // find maximum grade for class i with hours varying between 0 and h (inclusive)
                for (int k = 0; k <= h; k ++) {
                    // System.out.println("\t\tk: " + k);
                    int newGrade = gf.grade(i,k);
                    // System.out.println("\t\tnewGrade: " + newGrade);
                    int prevOpt = 0;
                    if (i - 1 >= 0) {
                        prevOpt = opt[i - 1][h - k].optGrade;
                    }
                    // System.out.println("\t\tprevOpt: " + prevOpt);
                    int current = newGrade + prevOpt;
                    // System.out.println("\t\tcurrent: " + current);
                    if (current > maxGrade) {
                        maxGrade = current;
                        kHours = k;
                    }
                }

                opt[i][h] = new Pair(maxGrade, kHours);
            }
        }

        return opt;
    }

    class Pair {
        int optGrade;
        int numHours;

        public Pair(int g, int k) {
            this.optGrade = g;
            this.numHours = k;
        }
    }
}
