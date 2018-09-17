package si;

import java.util.Random;

/**
 * @author aftab
 */
public class SI {

    public static Generation generation;

    public static void main(String[] args) {
        generation = new Generation(100);
        for (int i = 0; i < 20; i++) {
            generation.findBest();
            generation.changePosition();
            System.out.println(generation.best.toString());
        }
    }

    public static class Generation {

        Individual individuals[];
        int noOfIndividuals;
        public Individual best;

        public Generation(int noOfInd) {
            this.noOfIndividuals = noOfInd;
            individuals = new Individual[this.noOfIndividuals];
            for (int i = 0; i < this.noOfIndividuals; i++) {
                individuals[i] = new Individual();
            }
        }

        private void findBest() {
            Individual best = individuals[0];
            for (int i = 1; i < this.noOfIndividuals; i++) {
                if (best.fittness < individuals[i].fittness) {
                    best = individuals[i];
                }
            }
            this.best = best;
        }

        private void changePosition() {
            for (int i = 0; i < this.noOfIndividuals; i++) {
                individuals[i].changePosition(best);
            }
        }
    }

    public static class Individual {

        Random random;

        public double x;
        public double velocity_x;
        public double best_x;

        public double y;
        public double velocity_y;
        public double best_y;

        public double fittness;
        public double best_fittness;

        private double C1 = 0.1, C2 = 0.1;

        public Individual() {
            random = new Random();
            this.x = random.nextDouble();
            this.y = random.nextDouble();
            this.velocity_x = random.nextDouble();
            this.velocity_y = random.nextDouble();
            this.calculateFittness();
        }

        private void calculateFittness() {
            this.fittness = (100.0d * (x * x - y) * (x * x - y)) + (1 - x) * (1 - x);

            if (this.best_fittness < this.fittness) {
                this.best_fittness = this.fittness;
                this.best_x = this.x;
                this.best_y = this.y;
            }
        }

        private void changePosition(Individual gbest) {
            velocity_x = velocity_x + C1 * random.nextDouble() * (this.best_x - this.x) + C2 * random.nextDouble() * (gbest.x - this.x);
            double nx = this.x + velocity_x;
            if (nx >= -2 && nx <= 2) {
                this.x = this.x + velocity_x;
            } else if (nx < -2) {
                this.x = -2;
            } else {
                this.x = 2;
            }
            velocity_y = velocity_y + C1 * random.nextDouble() * (this.best_y - this.y) + C2 * random.nextDouble() * (gbest.y - this.y);
            double ny = this.y + velocity_y;
            if (ny >= -1 && ny <= 3) {
                this.y = this.y + velocity_y;
            } else if (ny < -1) {
                this.y = -1;
            } else {
                this.y = 3;
            }
            this.calculateFittness();
        }

        @Override
        public String toString() {
            return "x = " + this.x + "\t\t\t y = " + this.y + "\t\t\t fittness = " + this.fittness;
        }
    }
}
