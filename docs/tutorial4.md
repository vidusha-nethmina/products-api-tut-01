# Tutorial 4 - Inheritance

## Activity 0: where each member belongs

| Member | Declared in | Reason |
| --- | --- | --- |
| id : Long | Product | Every product needs an identity. |
| name : String | Product | Every product has a name. |
| downloadUrl : String | DigitalProduct | Only downloadable items need a download location. |
| price : double | Product | Every product has a price. |
| weightKg : double | PhysicalProduct | Shipped products have a shipping weight. |
| getName() : String | Product | Both subclasses inherit the same name accessor. |
| fileSizeMB : double | DigitalProduct | Downloaded files have a file size. |
| dimensions : String | PhysicalProduct | Shipped products have physical dimensions. |
| getPrice() : double | Product | Both subclasses inherit the same price accessor. |

Five of the nine members belong in Product. Declaring shared state once prevents inconsistent changes between subclasses.

## Activities 1 and 2: constructors and IS-A

DigitalProduct and PhysicalProduct extend Product. Each declares only its two new fields, has a no-argument constructor for JSON binding, and calls `super(id, name, price)` at the start of its full constructor. Public getters are inherited; private parent fields cannot be accessed directly by subclass code. Constructors are not inherited.

PhysicalProduct IS-A Product because it is a sellable item with an identity, name and price. A ShoppingCart HAS-A collection of products; it should not extend Product.

**Correction to the deliberate-break exercise:** Product already has a no-argument constructor. Removing the explicit `super(id, name, price)` therefore compiles because Java inserts `super()`. However, the supplied id/name/price are lost and remain null/null/0. A missing-default-constructor compiler error would occur only if Product had no accessible no-argument constructor. Keep the explicit call in both subclasses.

## Activity 3: one list, two types

ProductService seeds these into the existing `List<Product>`:

- ID 1: Clean Code (E-book), 29.99, /downloads/clean-code.pdf, 4.2 MB.
- ID 2: 27-inch Monitor, 249.99, 5.4 kg, 61cm x 41cm x 18cm.

The shared JSON properties come from Product's inherited public getters. Jackson also sees the runtime subclass getters. Both subclasses can be used wherever Product is expected, so GET /products and the controller remain unchanged. New products receive IDs starting at 3. Data is in memory and resets when the application restarts.

**POST binding detail omitted from the guide:** a `@RequestBody Product` parameter does not automatically become DigitalProduct just because the request contains downloadUrl. Product now uses Jackson's DEDUCTION subtype mapping with DigitalProduct and PhysicalProduct registered. Unique subclass fields select the type without adding a type property. Requests containing only common fields retain the original Product behavior. The subclasses also expose setters for explicit JSON binding. Send one product shape per request, not a mixture of digital and physical fields.

### Run and verify in Swagger

Use JDK 25, matching the existing pom.xml. In the project root:

```sh
# Windows PowerShell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run

# macOS / Linux
bash mvnw test
bash mvnw spring-boot:run
```

Open http://localhost:8080/swagger-ui/index.html .

1. GET /products: verify the two seeded objects and their common and unique properties.
2. POST /products with the following request body (replace Swagger's generated example if necessary):

```json
{
  "name": "Java Guide",
  "price": 12.5,
  "downloadUrl": "/downloads/java.pdf",
  "fileSizeMB": 2.5
}
```

3. GET /products and GET /products/3: verify the new digital fields survived.
4. GET /products/search?name=Java: verify the new item is found.
5. POST a physical product:

```json
{
  "name": "Keyboard",
  "price": 45,
  "weightKg": 0.8,
  "dimensions": "40 x 15 x 3 cm"
}
```

6. DELETE /products/3, then GET /products: verify the digital item was removed.

## Activity 4: UML

[Rendered hierarchy](uml/product-hierarchy.svg) and [editable PlantUML source](uml/product-hierarchy.puml).

Hollow triangles touch Product. Child boxes contain only their own members. Fields use `-` and public methods use `+`.

## Activity 5 and verification status

The required inheritance code, seeds, UML and these notes are committed together. The optional BundleProduct stretch is not part of the required submission and is not included.

ProductHierarchyTests covers inherited constructor values, mixed GET serialization, digital and physical POST round trips, ordinary Product POST compatibility, sequential IDs, search and deletion.

During preparation, Maven could not resolve the existing Spring Boot 4.1.1 parent because repo.maven.apache.org DNS/network access was unavailable. The available local JDK was 17, whereas this project targets 25. The test suite has **not** been executed successfully; run it with JDK 25 and Maven Central access before relying on runtime behavior. Existing dependency versions and controller behavior were preserved.

Partner comparison, neighbour initials and a personal Swagger observation must be completed by the student; no peer sign-off is claimed here.
