
#### What was tested:
-  App has 2 threads: one to add elements to map, and the second to sum values from this map; both threads perform 10_000_000 operations.
-  Time to add/sum 10_000_000 values is measured in ms
-  Java 8, 11, and 17 was tested separately
#### Results:

| Java Version | HashMap Time (ms) | ConcurrentHashMap Time (ms) | Collections.synchronizedMap Time (ms) | custom ThreadSafeMap Time (ms) |
|--------------|------------------|----------------------------|---------------------------------------|--------------------------------|
| Java 8       | 307 / 47794      | 289 / 66001                | 241 / 47848                           | 323 / 50317                    |
| Java 11      | 429 / 47388      | 283 / 53574                | 267 / 51490                           | 342 / 50618                    |
| Java 17      | 364 / 48246      | 355 / 103838               | 492 / 52610                           | 429 / 65571                    |

#### Conclusion:
ConcurrentHashMap and Collections.synchronizedMap are better that HashMap for concurrent access.
