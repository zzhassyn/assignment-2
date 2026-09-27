# Assignment 2 — Data Structures and Performance

## 1. Overview

This project implements three data structures in Java:

- Dynamic Array
- Linked List
- Min-Heap

The goal is to compare theoretical complexity with real benchmark results.

Project structure:

```text
assignment-2/
├── src/
│   ├── DynamicArray.java
│   ├── LinkedList.java
│   ├── MinHeap.java
│   ├── Benchmark.java
│   └── Tests.java
├── results/
│   ├── tables/
│   └── plots/
├── plot_results.py
└── README.md
```

---

## 2. Complexity Analysis

### Dynamic Array

| Operation | Best | Average | Worst | Auxiliary Space |
| --- | --- | --- | --- | --- |
| `add(x)` | Θ(1) | Θ(1) amortized | O(n) | Θ(1), or O(n) during resize |
| `add(index, x)` | Ω(1) | Θ(n) | O(n) | Θ(1), or O(n) during resize |
| `remove(index)` | Ω(1) | Θ(n) | O(n) | Θ(1) |
| `get(index)` | Θ(1) | Θ(1) | Θ(1) | Θ(1) |
| `contains(x)` | Ω(1) | Θ(n) | O(n) | Θ(1) |

`get(index)` is constant time because the array supports direct indexed access.

Insertion and removal can require shifting elements. When the internal array is full, a larger array is created and the old elements are copied. Because the capacity is doubled, repeated appends have amortized Θ(1) cost.

### Linked List

| Operation | Best | Average | Worst | Auxiliary Space |
| --- | --- | --- | --- | --- |
| `add(x)` | Ω(1) | Θ(n) | O(n) | Θ(1) |
| `add(index, x)` | Ω(1) | Θ(n) | O(n) | Θ(1) |
| `remove(index)` | Ω(1) | Θ(n) | O(n) | Θ(1) |
| `get(index)` | Ω(1) | Θ(n) | O(n) | Θ(1) |
| `contains(x)` | Ω(1) | Θ(n) | O(n) | Θ(1) |

This implementation stores only a `head` reference.

Access by index requires traversing nodes from the beginning of the list. Insertion or removal at index `0` is Θ(1), because only references near the head need to change.

### Min-Heap

| Operation | Best | Average | Worst | Auxiliary Space |
| --- | --- | --- | --- | --- |
| `insert(x)` | Ω(1) | O(log n) | O(log n)* | Θ(1), or O(n) during resize |
| `peekMin()` | Θ(1) | Θ(1) | Θ(1) | Θ(1) |
| `extractMin()` | Ω(1) | O(log n) | O(log n) | Θ(1) |

\* One insertion can become O(n) if the backing array must resize, but the heap adjustment itself is O(log n).

For index `i`:

```text
left child  = 2*i + 1
right child = 2*i + 2
parent      = (i - 1) / 2
```

The minimum element is always stored at index `0`.

---

## 3. Correctness

### 3.1 Dynamic Array — `add(index, value)`

The method shifts elements one position to the right before inserting the new value.

**Loop invariant:** before every iteration, the elements already processed on the right side are in their correct new positions and keep their original order.

**Initialization:** before the first iteration, no element has been moved yet, so the invariant is true.

**Maintenance:** each iteration copies `data[i - 1]` to `data[i]`. One more element is moved to its correct new position, while already moved elements remain correct.

**Termination:** when the loop finishes, all elements from the insertion position to the end have been shifted one position to the right. The new value is then written at `index`.

Therefore `add(index, value)` is correct.

### 3.2 Min-Heap — `insert(value)`

A new value is first placed at the end of the heap and then moved upward while it is smaller than its parent.

**Loop invariant:** before each iteration, the heap property is correct everywhere except possibly between the current node and its parent.

**Initialization:** the old heap was valid. Adding one new leaf can only create a possible violation between that new node and its parent.

**Maintenance:** if the current node is smaller than its parent, the two values are swapped. The only possible remaining violation is one level higher.

**Termination:** the loop stops when the node reaches the root or when it is no longer smaller than its parent. At this point, the full heap satisfies the min-heap property.

Therefore `insert(value)` is correct.

---

## 4. Experimental Setup

The benchmark uses:

```text
n = 100
n = 1,000
n = 10,000
n = 100,000
```

Each experiment is run **5 times**, and the average execution time is reported.

Timing is measured using:

```java
System.nanoTime()
```

A fixed random seed is used:

```java
new Random(42)
```

Input generation and structure construction are performed outside the timed sections.

### Workload 1 — Random Access

Structures:

- Dynamic Array
- Linked List

For each `n`, 10,000 random valid indices are generated and `get(index)` is executed for each one.

Measured metrics:

- average execution time;
- number of accesses.

### Workload 2 — Search

Structures:

- Dynamic Array
- Linked List

For each `n`, 1,000 search values are generated and `contains(value)` is executed.

Measured metrics:

- average execution time;
- number of comparisons.

### Workload 3 — Insertion and Removal

Structures:

- Dynamic Array
- Linked List

The benchmark measures:

- 1,000 insertions at index `0`;
- 1,000 insertions near index `n / 2`;
- 1,000 removals at index `0`;
- 1,000 removals near index `n / 2`.

Measured metrics:

- average execution time;
- element movements for Dynamic Array;
- node accesses for Linked List.

For `n = 100`, the structure does not contain enough elements for 1,000 removals. The original structure is therefore rebuilt between batches, and rebuilding is not included in the timed section.

### Workload 4 — Priority Processing

Structure:

- Min-Heap

For each `n`:

1. Insert `n` random integers.
2. Measure total insertion time.
3. Extract all `n` elements using `extractMin()`.
4. Measure total extraction time.
5. Count comparisons.
6. Verify that extracted values are in non-decreasing order.

For a single heap operation, insertion and extraction are O(log n). For the full workload of `n` operations, the total theoretical cost is O(n log n).

---

## 5. Results

Benchmark tables are stored in:

```text
results/tables/
```

Files:

- `random_access.csv`
- `search.csv`
- `insertion_removal.csv`
- `heap.csv`

Plots are stored in:

```text
results/plots/
```

### Random Access

![Random Access](results/plots/random_access_time.png)

### Search

![Search](results/plots/search_time.png)

### Search Comparisons

![Search Comparisons](results/plots/search_comparisons.png)

### Heap

![Heap](results/plots/heap_time.png)

### Measured Results

The benchmark results generally agree with the theoretical analysis.

For random access, Dynamic Array always performed exactly 10,000 element accesses, while Linked List node accesses increased strongly with `n`.

| n | Dynamic Array time (ns) | Linked List time (ns) | Dynamic Array accesses | Linked List accesses |
| ---: | ---: | ---: | ---: | ---: |
| 100 | 426,420 | 1,389,700 | 10,000 | 511,327 |
| 1,000 | 60,360 | 7,731,720 | 10,000 | 5,021,262 |
| 10,000 | 62,700 | 89,106,400 | 10,000 | 50,180,278 |
| 100,000 | 468,260 | 1,052,641,460 | 10,000 | 504,933,532 |

For search, both structures performed the same number of comparisons, but the Dynamic Array became much faster for large inputs.

| n | Dynamic Array time (ns) | Linked List time (ns) | Comparisons |
| ---: | ---: | ---: | ---: |
| 100 | 613,180 | 489,540 | 78,252 |
| 1,000 | 1,785,980 | 2,301,020 | 793,961 |
| 10,000 | 5,164,220 | 19,849,320 | 7,761,001 |
| 100,000 | 35,052,280 | 229,602,620 | 77,643,801 |

For insertion and removal, operations at the beginning of the Linked List stayed very cheap. Dynamic Array operations at the beginning required many element movements. For middle positions, both structures required linear work, but for different reasons: Dynamic Array moved elements while Linked List traversed nodes.

The heap benchmark also showed increasing execution time as `n` increased:

| n | Insert total time (ns) | Extract total time (ns) | Insert comparisons | Extract comparisons |
| ---: | ---: | ---: | ---: | ---: |
| 100 | 30,040 | 74,660 | 217 | 858 |
| 1,000 | 86,340 | 216,120 | 2,188 | 14,995 |
| 10,000 | 1,313,100 | 1,799,480 | 22,582 | 216,644 |
| 100,000 | 2,773,840 | 15,937,840 | 229,018 | 2,831,804 |

---

## 6. Discussion

### Random Access

The results match the theoretical complexity well.

Dynamic Array uses direct indexing, so `get(index)` is Θ(1). Its access count stayed constant at 10,000 for every input size.

Linked List must traverse nodes from the head to the requested index. Its access count increased from about 511 thousand at `n = 100` to more than 504 million at `n = 100,000`.

This explains the large difference in execution time for large inputs.

### Search

Both structures use linear search, so both have Θ(n) average and worst-case behavior.

The number of comparisons was identical for both structures because they searched the same values in the same data.

However, Dynamic Array was much faster for large inputs. For `n = 100,000`, Dynamic Array took about 35 ms, while Linked List took about 230 ms.

This shows that two algorithms with the same Big-O complexity can still have different practical performance.

### Insertion and Removal

At the beginning of a Dynamic Array, many elements must be shifted. Therefore the number of movements grows as `n` increases.

A Linked List can insert or remove at the beginning by changing only a few references, so these operations remain Θ(1).

For middle positions, both structures usually require Θ(n) work:

- Dynamic Array spends time moving elements.
- Linked List spends time traversing nodes.

### Min-Heap

The Min-Heap is efficient for priority processing.

`peekMin()` is Θ(1), while insertion and extraction require at most logarithmic heap adjustment in normal operation.

The total time for inserting and extracting all `n` elements increased with input size, which is consistent with the expected O(n log n) total workload.

### Theory vs Practice

The measured timings do not form a perfectly smooth mathematical curve.

For example, the Dynamic Array random-access time for `n = 100` was higher than for `n = 1,000` and `n = 10,000`.

This can happen because real JVM execution is affected by:

- JIT compilation;
- cache behavior;
- memory allocation;
- garbage collection;
- background system activity;
- constant factors.

This is why operation counts are useful together with execution time.

---

## 7. Design Recommendations

### Dynamic Array

A Dynamic Array is a good choice when:

- fast random access is important;
- most insertions happen near the end;
- iteration speed matters;
- good memory locality is useful.

### Linked List

A Linked List can be useful when:

- insertion or removal at the beginning is frequent;
- random indexed access is not important.

It is less suitable for workloads with many `get(index)` operations.

### Min-Heap

A Min-Heap is a good choice when:

- elements are processed by priority;
- the minimum element must be available quickly;
- repeated insertion and minimum extraction are required.

---

## 8. Conclusion

The experiments show that the best data structure depends on the workload.

Dynamic Array performed very well for random access and was faster than Linked List for large linear searches.

Linked List performed very well for insertion and removal at the beginning, because those operations only require changing references.

Min-Heap provided efficient priority-based processing, with constant-time minimum access and logarithmic heap adjustment.

The assignment also demonstrates that Big-O complexity describes growth, but real performance is also affected by implementation details, memory layout and JVM behavior.

---

## Build and Run

Compile:

```bash
javac src/*.java
```

Run tests:

```bash
java -cp src Tests
```

Run benchmarks:

```bash
java -cp src Benchmark
```

Generate plots:

```bash
python plot_results.py
```
