# Reviewers and Reviewees

A Kotlin implementation.

## Build

1. clone this repo
2. `cd <local-repo-directory>`
3. `mvn clean install`

## Run

1. ensure `cd <local-repo-directory>`
2. `java -jar target/reviews-1.0-SNAPSHOT-jar-with-dependencies.jar`

```bash
$ java -jar target/reviews-1.0-SNAPSHOT-jar-with-dependencies.jar
└─ Alfred Hitchcock (A9)
   ├─ Cary Grant (A8)
   │  ├─ Peter Lorre (A6)
   │  ├─ Toshiro Mifune (A7)
   │  └─ Hedy Lamarr (A6)
   │     ├─ Marie Dressler (A6)
   │     ├─ Yul Brynner (A6)
   │     │  ├─ Waheeda Rehman (A5)
   │     │  └─ Robert Donat (A4)
   │     │     └─ Lionel Barrymore (A3)
   │     ├─ Meena Kumari (A1)
   │     └─ Hema Malini (A1)
   ├─ Grace Kelly (A8)
   │  └─ Omar Sharif (A7)
   ├─ Jean Harlow (A8)
   └─ Vivien Leigh (A8)
      ├─ Steve McQueen (A7)
      │  ├─ Montgomery Clift (A6)
      │  ├─ Alain Delon (A6)
      │  ├─ Ethel Waters (A6)
      │  └─ Anna May Wong (A6)
      ├─ Doris Day (A7)
      ├─ Carole Lombard (A6)
      └─ Sidney Poitier (A4)

```





