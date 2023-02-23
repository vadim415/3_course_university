#include <iostream>
#include <omp.h>

#define ROWS 1000
#define COLS 1000

using namespace std;

void sumOfElements(int arr[][COLS], int* sum) {
#pragma omp parallel for shared(4)
    for (int i = 0; i < ROWS; i++) {
        for (int j = 0; j < COLS; j++) {
#pragma omp atomic
            * sum += arr[i][j];
        }
    }
}

void minRowSum(int arr[][COLS], int* min_sum, int* min_row) {
    *min_sum = 0;
#pragma omp parallel for shared(min_sum, min_row)
    for (int i = 0; i < ROWS; i++) {
        int row_sum = 0;
        for (int j = 0; j < COLS; j++) {
            row_sum += arr[i][j];
        }
#pragma omp critical
        {
            if (row_sum < *min_sum) {
                *min_sum = row_sum;
                *min_row = i;
            }
        }
    }
}

int main() {
    int arr[ROWS][COLS];
    int sum = 0, min_sum, min_row;
    omp_set_num_threads(4);
    // Initialize array with random values
    for (int i = 0; i < ROWS; i++) {
        for (int j = 0; j < COLS; j++) {
            arr[i][j] = rand() % 100;
        }
    }

    double start_time = omp_get_wtime();

    // Calculate sum of all elements in parallel
    sumOfElements(arr, &sum);

    // Calculate row with minimum sum in parallel
    minRowSum(arr, &min_sum, &min_row);

    double end_time = omp_get_wtime();
    double elapsed_time = end_time - start_time;

    // Output results and elapsed time
    cout << "Sum of all elements: " << sum << endl;
    cout << "Row with minimum sum: " << min_row << " (sum = " << min_sum << ")" << endl;
    cout << "Elapsed time: " << elapsed_time << " seconds" << endl;

    return 0;
}
