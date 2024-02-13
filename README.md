# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```


## Phase 2 Server Design Link
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAFIRJoDiwAtigBLA0AEwS1c+QsSHMAMhADmHQSLFQ6kxjJgAlFEoDOYSitHj6UpjHlK0FaoRSYNDaaT2GwUYAQh3KVI4ultpsHNx89oE0zhKuVgAiPsAAgiAgKAYGmMLJAEbABigwwnnOwVqkcAAWQgooALIoAGyYClAQAK7YMADEeQidxR5IRt6+aH0A7tVIxpi9iKikSjQGMJ3rm5RovCgANDC4WVPQwodCwjAoPMBICIdQKGCdUGjraChTMMCdYNUAFQgAGt0D8RDAQE8fJkYJ9vtt1Et0MwALQAPnIAUoAC4YABtAAKAHkyACALowAD0iIAOmgAN6I3Z8Q7HAynKDna63e4AX0wUUoGJGRhMlzUOKenkoAApmXs2YUOWdDjc7ggAJSYUXGKCmNQi-SjLw+JB+KKOHE6ACinAAkmSbToNkU3oqjsrOdz1fcdcaxmaLQFHDAMTAkmBUulMgY8fUwABVN3yt0slDayPRjJZMOY0p4sg22Q2uAA107PYwABmHR4FfUvSQ1Yb6Zgox+CGhwgAnjAo6C0Jgs2kc+tUeHdeNzf4HBk8Qq+ILsVAADwTqdB2eBeeUDqwWXALsoYC99uTbbaoWwCeY5EYPEASHxABYAAwAZkp9IZMAARHwWTAPUf54n+NpQPueJHt2fYDugf4wAKTYtouxQdsAVAasAAxOCOMa5reugBqaEyWvOcKdAgCD+p407BnOxThvhY54lCJ7GMmcpoUqJxnJmySjrGeYlHkeL2gAckWOjljxnp8Vyaq8ggMCSQCxINrRJr0duobMYJBFxpC0LGCkfzVKmlZ8AJUZCYR+ZiapUnOrJaYer8-xAoOTnqT85nDgZY5hpOJE6eRKDQeZXnoFpgZkSGGTBZi16RZ5IIxdeSXwMgKJ4gATG+b4-h5gLpWgAroMIzjtF0PS9NAdTFDYHDTLM8yLDlKxIGsrqtlWlzySqikwE8LxvB8Xx+Wlg6YPeaLJSueJEqSFLUkUWQzj+cnst6ArXhizVoAauIIIoHCWe6rKDd62qHcdUAHWdunzrIxIOhJfVXTtZyYId4UYixsY4gmXFQBd6a8UNwjar01YcFciIAOTrLkUYBbZhkiQW5DFqWrlWcUA3fVyNZ1ppKHXWcJQQJkaCI8wtxgCA1To9msa3n9CURQAYkeRS-U9-0Yile7QMu1CUFlc1Le+ACM36Mv+gEGMBKCgf+EFQRsuzmdASAAF4oMIiHIc2lMk8INPrIzzOs3ZBgc4LXM4mhCy3KCcKTSV0VDpzjEA4FQPsTCZn-ODew2Wz9mieJzkyZ9BxTaV3lqRpJV24Zju2OFOLe2VAvZ1zt4pXnM2ZUR0swAVRWMqX6AVSI1UdN0fTtCgYI2F0zC9DMcxOHNIkpQSCS4wCNqUlS60GJtOvTRlK6PYXjE4qdChd4eUVlTDIAQDwEjFEjKPJAXHD-eigNZDiNvVKDG9z2gkf21jjkNCkAJwPwSc+zAUxzNU5NmzrpMS2tN6YwGvhnMcWdT7O15ggfmfsdwoGLotUW6hy7hkrq+N88sYA-iVrGVW6twKQWgAuWe1Q9aG2NkhBYgDN7eRAdbHwtsL4O0XjA5eQCT7PWQefQOl9hAoDEKZcyd9k7oEfpjcM2NbQNGJAANRtF-MqpNd5JwWCAMQeAYBTxnJA4SGIwhcD2JaGgeIbQSQSJCV4TxSB6L8AsUarxJhAJgLKAwlCqJXDyMUNAVEtQGKyCgiWUAcT+OopgSq5Qaot16E8K4sgTQwAiLCHubUnC9C0csZgqxYSbBUYOQ4UxijOLeDABQVYODVggLNTq80sShKWpwG0q0qSVL4PSbh+10TGNSfdFeJpUkGHET7beu994NmRiUY+fS9j3Q4bw4GzwKAbT8KMredCWxuKYXTBmLCWaIMcBiAsr936fzcb-f4mlSjQKWXA-mm54qMSykPNB4saA3kwfUmWOCFa-gAoQkCYFNZkO1iVKhRsTZbMKWCJh4CDmHB3t4kazwXHgKogQbAYgKlVntAkbIpQkpzMiCGcxmkSUoDMUxdE2MiwljLFM9YDihyYEpQs3p7ATGkocOYhMqTZQdJQPi7UlLqXP0LLjRlQr8X7F7sYOS9IBigGBHJAaQqJJ7CcWi8pQrzw1LZVy1J4qiJDxlQkOVGTFVoGVSAVVbkrrqr2JqpcGC7w-IJNXf5-4hUGHVviBkf5zXq1lnlD8L5Dh-nlSgUG6ZQJ-j-Icekf5bXAljXseNiafXOr2DiBNfIYDkgblVNozc6rYFeNi4ocATLFFSa1Pumj6kVO6rCDV7kIRAMOG6T5qKxrvB+J7b4Qq6k5MHotAkJIyQTyFV0hh6Afztr4HtBenLwjzIlLiYOxgBVAPGXvPAB83TTNRsAQ166+AcrYcssAqzp7rL3UE8cDk8RnI-rCyYVz-4sphTsq2eyEVMxZmwrKTyZw5xgA8pwYGGJINeRO95tyjFGtMWSiK5qPFXhXFLD12DcH4MBUBYFGtSFhPBbrKABsoW0Ipn+2E18kVdAQFcMpkweCYuIDits+Lsjss3Q9Nd3KqVoZvakl1KBBU5r4AAH3NaKlDPLoh8ILPSvGuK+Die1f29T6E0AGrFWh28fHVC4giTRYzZgBMGd5RFbdKABVLozOeoT1KTliUktJcs5r+wQCxGs1l1mlMhM+cDPY+KPmSwrrhr1eDFZBrCwkENYaXy0OiaW2qfQezCNOt8YxKSqz9AQCq0dKJx1NMnYmNps6KE+x-HNOAEBTpQEOOaldoTkMXoEPxnEAArBTEmu3ZRyQ1prLWEv7smYfGZUZDj-A7HkrYi2CbrAGoiabZ6LOGkE8akTCY70zg2YOGG9D77U1AfsoDT6JUwDfRc+dn6-66NjPotht4YP3L5tB0KW5wrwfK+8t1Q3coEjlt6wjKtiMkK1p0ChkKaGm22fds7zCgOMZRaxjFCAsVcbxQSrT6K9XVNqYF1tRn+sDIwwYeTnWOUk75c8Bz43nM7Zs25nEqnpUJeKVah1idU1yUc8z1DrOOtCYpwloXl7+Oi5Z0p3r-XJN8FlUDjAI3oDU5c4Z2lYkOflnq41sWFMd5NfbCt48p44LABmnT5BMuN0mbCSAWxKJQaaaQ9t4XcuzMRa+e6nJvyvyxYBcrIhILSPQXN2eeCaBoVG4N7ADCWF7g4TEJL4TrPtdp9c9rnEHmXIJ3PCrsAauwZoW1FnwzwtFoAigEMH3OH-eesKuSKJjd0txPaMAPsiATywGANgbFhAtwNvmAPcMQ98QjxLGPCepQfcBwxqxLRvfZTl5AzIxyU-WnKKPCpU9T7gu4hr3XwHWCvUH6r00qD9eouN-xAAVmb0HghRG1Zh61kIgwUJiATGhdEoAA
