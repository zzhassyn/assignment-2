import os
import pandas as pd
import matplotlib.pyplot as plt

os.makedirs("results/plots", exist_ok=True)


def plot_random_access():
    df = pd.read_csv("results/tables/random_access.csv")

    for structure in df["structure"].unique():
        part = df[df["structure"] == structure]

        plt.plot(
            part["n"],
            part["averageTimeNs"],
            marker="o",
            label=structure
        )

    plt.xlabel("n")
    plt.ylabel("Average execution time (ns)")
    plt.title("Workload 1 - Random Access")
    plt.legend()
    plt.grid(True)
    plt.xscale("log")
    plt.tight_layout()

    plt.savefig("results/plots/random_access_time.png")
    plt.close()


def plot_search():
    df = pd.read_csv("results/tables/search.csv")

    for structure in df["structure"].unique():
        part = df[df["structure"] == structure]

        plt.plot(
            part["n"],
            part["averageTimeNs"],
            marker="o",
            label=structure
        )

    plt.xlabel("n")
    plt.ylabel("Average execution time (ns)")
    plt.title("Workload 2 - Search")
    plt.legend()
    plt.grid(True)
    plt.xscale("log")
    plt.tight_layout()

    plt.savefig("results/plots/search_time.png")
    plt.close()


def plot_search_comparisons():
    df = pd.read_csv("results/tables/search.csv")

    for structure in df["structure"].unique():
        part = df[df["structure"] == structure]

        plt.plot(
            part["n"],
            part["averageComparisons"],
            marker="o",
            label=structure
        )

    plt.xlabel("n")
    plt.ylabel("Average comparisons")
    plt.title("Workload 2 - Comparisons vs n")
    plt.legend()
    plt.grid(True)
    plt.xscale("log")
    plt.tight_layout()

    plt.savefig("results/plots/search_comparisons.png")
    plt.close()


def plot_heap():
    df = pd.read_csv("results/tables/heap.csv")

    for operation in df["operation"].unique():
        part = df[df["operation"] == operation]

        plt.plot(
            part["n"],
            part["averageTimeNs"],
            marker="o",
            label=operation
        )

    plt.xlabel("n")
    plt.ylabel("Average execution time (ns)")
    plt.title("Workload 4 - Min Heap")
    plt.legend()
    plt.grid(True)
    plt.xscale("log")
    plt.tight_layout()

    plt.savefig("results/plots/heap_time.png")
    plt.close()


plot_random_access()
plot_search()
plot_search_comparisons()
plot_heap()

print("Plots created in results/plots/")