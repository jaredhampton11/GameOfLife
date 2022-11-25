public class GameOfLife {
    public static void main(String[] args) {
        int height = 0, width = 0, maxGeneration = 0;
        double weight = 0.0;

        if (args.length != 4) {
            System.out
                    .println("Error: Please execute program using 'java GameOfLife height width maxGeneration weight'");
            System.exit(1);
        }

        try {
            height = Integer.parseInt(args[0]);
            width = Integer.parseInt(args[1]);
            maxGeneration = Integer.parseInt(args[2]);
        } catch (Exception e) {
            System.out.println("Error: Please input valid integers for the height, width, and weight");
            System.exit(-1);
        }
        try {
            weight = Double.parseDouble(args[3]);
        } catch (Exception e) {
            System.out.println("Error: Please input a valid double in the range [0,1] for the weight");
            System.exit(-2);
        }

        // Grids will alternate between being the current grid and the previous grid
        int[][] oddGrid = new int[width][height];
        int[][] evenGrid = new int[width][height];

        // Randomly distribute
        generateInitial(height, width, weight, evenGrid);
        // generateTest(height, width, weight, evenGrid);

        int currentGen = 1;
        while (currentGen < maxGeneration) {
            System.out.println("Generation " + currentGen + ":");
            if (currentGen % 2 == 1) {
                cycle(height, width, evenGrid, oddGrid);
            } else {
                cycle(height, width, oddGrid, evenGrid);
            }
            currentGen++;
        }
    }

    public static void generateInitial(int height, int width, double weight, int[][] grid) {
        int lifeNeeded = (int) (height * width * weight);
        int lifeGenerated = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (Math.random() <= weight) {
                    grid[x][y] = 1;
                    lifeGenerated++;
                } else {
                    grid[x][y] = 0;
                }
            }
        }
        if (lifeGenerated != lifeNeeded) {
            generateInitial(height, width, weight, grid);
        } else {
            System.out.println("Generation 0:");
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (grid[x][y] == 1) {
                        System.out.print("* ");
                    } else {
                        System.out.print("- ");
                    }
                }
                System.out.print("\n");
            }
        }
    }

    public static void generateTest(int height, int width, double weight, int[][] grid) {
        System.out.println("Generation 0:");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 1) {
                    System.out.print("* ");
                    grid[x][y] = 1;
                } else {
                    System.out.print("- ");
                    grid[x][y] = 0;
                }
            }
            System.out.print("\n");
        }
    }

    public static void cycle(int height, int width, int[][] gridPrev, int[][] grid) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int livingNeighbors = 0;
                for (int i = 0; i < 8; i++) {
                    livingNeighbors += neighborValue(gridPrev, x, y, i);
                }
                if (livingNeighbors < 2 || livingNeighbors > 3) {
                    grid[x][y] = 0;
                    System.out.print("- ");
                } else if (gridPrev[x][y] == 1) {
                    grid[x][y] = 1;
                    System.out.print("* ");
                } else if (gridPrev[x][y] == 0 && livingNeighbors == 3) {
                    grid[x][y] = 1;
                    System.out.print("* ");
                } else {
                    grid[x][y] = 0;
                    System.out.print("- ");
                }
            }
            System.out.print("\n");
        }
    }

    public static int neighborValue(int[][] grid, int x, int y, int i) {
        try {
            switch (i) {
                case 0:
                    return grid[x - 1][y - 1];
                case 1:
                    return grid[x][y - 1];
                case 2:
                    return grid[x + 1][y - 1];
                case 3:
                    return grid[x - 1][y];
                case 4:
                    return grid[x + 1][y];
                case 5:
                    return grid[x - 1][y + 1];
                case 6:
                    return grid[x][y + 1];
                case 7:
                    return grid[x + 1][y + 1];
                default:
                    return 0;

            }
        } catch (Exception e) {
            return 0;
        }
    }
}
