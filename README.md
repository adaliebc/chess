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
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAEooDmSAzmFMARDQVqhFHXyFiwUgBF+wAIIgQKLl0wATeQCNgXFDA3bM4hlNJwAFtPYoAsigCMpyU3jW0thwCYXjacxWNvYoAMx+5oEeXigALBFuQZ4hAKwJAe7BDgBsmOxQEACu2DAAxNoIhQZsnDx8AmhlAO6WSGBipYiopJw0XDCF-YOUaMAAtigANDC4ak3QGtPSGjAoY8BICNNQKGCFUGj9aChNMMCFYJYAKhAA1uhnaCsgO-yqMMenw1CYXejMAFoAHzkSg0KAALhgAG0AAoAeTIVwAujAAPTfAA6aAA3t9RhNprMuPMoItVutNgBfTDCcEwYGsDjcXj8JCCOmiKE7WrtKAACnx4ymMz0JIW0zWGwQAEpMDUWfV2UIwaIGSC5GBFMpVFwobYwABVfQCoUTOWa7UqNTqwzaKFkACiABlHXArgMTQSDAAzApjT20UpIH2Bg7CmDcM4IV4aACeMC19zQmEtSmt-QBjIVdTZHNVKihZrEOdZDU5KgZjLplChlAKPxrsCzIL+GChAEhobEAAyhVHYnEwABEEzUwFsw6hw8dUAbUOAMZQwHjieAyeHMBpwdDxcj-WAVGlwAqYjTOptLaZvKV+ZEhY+hQQCHlzNz5YLBkZ54zUJey-aY1KEFL1hSJMVSQ0C15HTXVbSMKEAEkADknRYD1i3AuYJQpaUYBQq54TDV8bzzFV7y-DUYIvPUYH-N4FAuSwQJGYVoK1WDLxBBD8NQx10LDb0liYm5k14wiziY1NqIzKsQVLW9yKoLlJMuUT0BIxUyIrL9qzBWtVOuO4NKbOT4GQf4oW8Hse0Hc41OMtAaXQDQTHyIoSlKaBghgZ0IE4RpShaNoOjbZheneQZBIjZZRWwsltl2fZDg+E5DPUlMwttJsoThREUXRfQ1GVQdMLi8UyRpUzszfMtlR0qEEH8pA0BY8NCXKyC5QU7TP1tH9dX1XYgNNUCOuJLrMFKH0WpWb4AHJ+i0LVpI4mj4PtcgXTdDCxoMWKJoWGA-QgANvimkNOqOjQIFUNB5uYdYwBASxVqtOCrx6j8KKhAAxRd9E0996r6q8ctWOdoFpfTm0ZMLct7RwB1xEcxy4CcUCnEdZ3nAZRiY6AkAALxQDRN23S7DrJQxbv6J6XrezjMxq0jvuUh9iym9Z7lS057KM5Mgbqu92comABrUP9XnaRjLja712Peri7SQviBLK-mMvEoj+cZ9bPtqxSGvSxyhaN0G9OoAzNdN6q4Ys9sYGs2zcRt5NnKeNyCmKMp8hQB4-PYIpmCC1p2l+B3ARBcHoRkbarkdVE0SKrgSvxhzBeq+TDd6n6YCaoOLn5N30G6nO2bVb8ZMGmB6csEbi5Exy5VKEBTtwHYw0Wwx5D12TGR4uwFCuOAAAkTbEpo2ksYidwnh4bruh7a-4BmJeZ7PWZBvP-oQQGvu30WzPB+sobt1tI4RnskZgQdUd1DGsZnSHITx-nCZJsmtwu0MS8aRe6ar1euvMyB8RYqT-mbXOR8q5rV-IYFACBdgoFlsxP+ismYbShCwR0dh4QADVHTz0aCdAMutSg7D2AcYhMB+RcEsEUBAKxtAGDQE+WUfcPosy0hXB8bDnxQN4bpaOMMoT8JfOfcy3QwC5WdsiTALkTB5G9p5HYKxnQshgAAcWFP0UOIUpogEjjACKQwDxN2TNMJoBhKHJRMRGFqPoIAR2kdlURMItGOgKmidgwpsSQKzteHhh8VLIB4DoscjcM6l0ESEyssCla0QNBQYqggokCxia3dueADALSWr3EBA9NpDxHuPP+MAp6XBgCnZUP8aEAPuo9IBnDLzcOBuAh8u997lzicI0EVtX6n0bDDMy8MYSI2RkOUcD9JzThxtAIs6cGFQGJqTcmdTykAJXs9Sw0w25PhWLY6hYwnwEGwEg+xEx8IyHUIUtpwslIqV8RMRCMg6Fly3h0sW68hpgAiSgfkzyUCvIwfrbim0nSundJc4FMhJjBXaGVbEFRQC3A1k8GFyFhRTSOY0IFkY0BOJaRvIJ7THkPiBa8+FYcUBIrQCikAaK9pLAxUCrFExYlfOPu4ylcKEW0uZTABlTLWIdVimy7FkixnQjkbfFGw4gVcCxtCHECrhSvKxo4bwoRYjTGHPyka3opzDmHNMbEw5hWGuFMa01I4JUTAhCaqkMBkQe1csojyZRsD7HOQYOA0sDD-OaDSwxxjTGYrAo8FYf9pgmnpLig8vMYUuP+G4gZuUERIiTkC-xFj0CDntSgKqIz7nmzzvRdo-z0kZQ+cErlCSma-JSanNJ6DDHZM7nknuK1CngqhCUseNDKkzxqYIDZeb-600adste1dWmbzreSlAf0AYlh6fWy24I6wv2hgM0Zl9xnX0mffccszsYv0We-FZn91lz02bTGduy6KMMOUlY5pziAXO9Nc25c6SVgKXfqdVbyuC1rJTpfqf7fn-PZQCwtAAfXloL+59q2lCj0hacVvrxQ4wlzi7kLvA5+MR7DOVLsg3AmuFaUBVsLchj6qGUJoQw8BxMEBQSpJTAR0lDzja8rIxBsGPLgO7vpFeaVsq75qpeTITV2rYjf0UR6n2pQ4yIKaqcAAUhAFq2iIzlAQKilNpBN0GThIabxOalkZUHGFOAEAmpQGmLy4te7S3QJUgAKx02gKtMapH-Hs455zwGwO8dBlRSjksTG7Gbcqatzd21jA7rkk03dlrAGmJcKM4aorFgPBi743bgDEqwTAAdZSJ0VOntU3UtS71VYacvOupWDafMAzALpa72uCdM4MndUqD3diPXKqZaNH5zIvW-Am161nfwa9Eyd7w657JfTAXFtcP2+o+BGV5v6ov-vXR13l7yBMRfFlBmLfzhSAtC611DkKdowqpRUmlZVhVlUwxQ7DMKCVEu4wBvjInAfnZ+TAbzLVaPAemHZhz0B6PKx4o96FsPHN1Lbo5-c0ZYwJiTBpAHR3jYgH2DsDAI1YNnYoty9NEMGyicoPu6RV9+yjZPejM9z9caLhx2uDc83LoY+gFjw8x5TytYbTREjAiCM8SY-xXaoqDC6dR9AeWbFxeEfC3nK4UAqiU5gX1qXEiS32yZzCORCjPbKc8vkYACZEDLlgMAbA5zCBkWDQYrKhuYRx1dAnJORh6fNk12W0Wf4kF4H5GF0PldIuJPD47qP92Vbi3jkQxcCBisa54zHh8Ou9cg6p0Jmn4ig+M8submy8iCc9eI511d+vK7e6GWX8TQ2UhV9Z9M09mNJu4w0KoF4xAGjrMUUAA
