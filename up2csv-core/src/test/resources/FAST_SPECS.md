# Specification of Invoice `FAST` Format

- `001` [Invoice](#d001)
    - `002` [Seller](#d002)
    - `003` [Buyer](#d003)
    - `007` [Payer](#d007)
    - `008` [Payee](#d008)
    - `005` [Notes](#d005)
    - `004` [Items](#d004)
        - `009` [Item attributes](#d009)
    - `006` [Charge or Allowance amounts](#d006)

<h2 id="d001">`001` Invoice </h2>

- <b>Segment:</b> `01` Invoice
- <b>Cardinality:</b> `1..1`
- <b>Class:</b> [io.github.up2jakarta.test.impl.dto.Invoice](../java/io/github/up2jakarta/test/impl/dto/Invoice.java)

|         | 1        | 2          | 3          | 4            | 5          | 6          |
|---------|----------|------------|------------|--------------|------------|------------|
| Code    |          | I01        | I02        | I03          | I04        | I05        |
| Name    | #Segment | Invoice id | Issue date | Gross amount | Net amount | Tax amount |
| Default |          |            |            |              |            |            |

<h2 id="d002">`002` Seller </h2>

- <b>Segment:</b> `02` Seller
- <b>Cardinality:</b> `1..1`
- <b>Class:</b> [io.github.up2jakarta.test.impl.dto.Party](../java/io/github/up2jakarta/test/impl/dto/Party.java)

|         | 1        | 2          | 3    | 4       | 5    | 6        | 7                | 8                |
|---------|----------|------------|------|---------|------|----------|------------------|------------------|
| Code    |          | I01        | P01  | P02     | P03  | P04      | P05              | P06              |
| Name    | #Segment | Invoice id | Name | Country | City | ZIP code | Address 1st line | Address 2nd line |
| Default |          |            |      | TN      |      |          |                  |                  |

<h2 id="d003">`003` Buyer </h2>

- <b>Segment:</b> `03` Buyer
- <b>Cardinality:</b> `1..1`
- <b>Class:</b> [io.github.up2jakarta.test.impl.dto.Party](../java/io/github/up2jakarta/test/impl/dto/Party.java)

|         | 1        | 2          | 3    | 4       | 5    | 6        | 7                | 8                |
|---------|----------|------------|------|---------|------|----------|------------------|------------------|
| Code    |          | I01        | P01  | P02     | P03  | P04      | P05              | P06              |
| Name    | #Segment | Invoice id | Name | Country | City | ZIP code | Address 1st line | Address 2nd line |
| Default |          |            |      | TN      |      |          |                  |                  |

<h2 id="d007">`007` Payer </h2>

- <b>Segment:</b> `07` Payer
- <b>Cardinality:</b> `0..1`
- <b>Class:</b> [io.github.up2jakarta.test.impl.dto.Party](../java/io/github/up2jakarta/test/impl/dto/Party.java)

|         | 1        | 2          | 3    | 4       | 5    | 6        | 7                | 8                |
|---------|----------|------------|------|---------|------|----------|------------------|------------------|
| Code    |          | I01        | P01  | P02     | P03  | P04      | P05              | P06              |
| Name    | #Segment | Invoice id | Name | Country | City | ZIP code | Address 1st line | Address 2nd line |
| Default |          |            |      | TN      |      |          |                  |                  |

<h2 id="d008">`008` Payee </h2>

- <b>Segment:</b> `08` Payee
- <b>Cardinality:</b> `0..1`
- <b>Class:</b> [io.github.up2jakarta.test.impl.dto.Party](../java/io/github/up2jakarta/test/impl/dto/Party.java)

|         | 1        | 2          | 3    | 4       | 5    | 6        | 7                | 8                |
|---------|----------|------------|------|---------|------|----------|------------------|------------------|
| Code    |          | I01        | P01  | P02     | P03  | P04      | P05              | P06              |
| Name    | #Segment | Invoice id | Name | Country | City | ZIP code | Address 1st line | Address 2nd line |
| Default |          |            |      | TN      |      |          |                  |                  |

<h2 id="d005">`005` Notes </h2>

- <b>Segment:</b> `05` Notes
- <b>Cardinality:</b> `0..n`
- <b>Class:</b> [io.github.up2jakarta.test.impl.dto.Note](../java/io/github/up2jakarta/test/impl/dto/Note.java)

|         | 1        | 2          | 3   | 4       |
|---------|----------|------------|-----|---------|
| Code    |          | I01        | A01 | A04     |
| Name    | #Segment | Invoice id | Key | Content |
| Default |          |            |     |         |

<h2 id="d004">`004` Items </h2>

- <b>Segment:</b> `04` Items
- <b>Cardinality:</b> `1..n`
- <b>Class:</b> [io.github.up2jakarta.test.impl.dto.Item](../java/io/github/up2jakarta/test/impl/dto/Item.java)

|         | 1        | 2          | 3       | 4       | 5        | 6            | 7          | 8          |
|---------|----------|------------|---------|---------|----------|--------------|------------|------------|
| Code    |          | I01        | I06     | I07     | I08      | I03          | I04        | I05        |
| Name    | #Segment | Invoice id | Item id | Product | Quantity | Gross amount | Net amount | Tax amount |
| Default |          |            |         |         | 1        |              |            |            |

<h2 id="d009">`009` Item attributes </h2>

- <b>Segment:</b> `09` Attributes
- <b>Cardinality:</b> `0..n`
- <b>Class:</b> [io.github.up2jakarta.test.impl.dto.Attribute](../java/io/github/up2jakarta/test/impl/dto/Attribute.java)

|         | 1        | 2          | 3       | 4   | 5     |
|---------|----------|------------|---------|-----|-------|
| Code    |          | I01        | I06     | A01 | A02   |
| Name    | #Segment | Invoice id | Item id | Key | Value |
| Default |          |            |         |     |       |

<h2 id="d006">`006` Charge or Allowance amounts </h2>

- <b>Segment:</b> `06` Amounts
- <b>Cardinality:</b> `0..n`
- <b>Class:</b> [io.github.up2jakarta.test.impl.dto.Amount](../java/io/github/up2jakarta/test/impl/dto/Amount.java)

|         | 1        | 2          | 3   | 4     | 5           |
|---------|----------|------------|-----|-------|-------------|
| Code    |          | I01        | A01 | A02   | A03         |
| Name    | #Segment | Invoice id | Key | Value | Description |
| Default |          |            |     |       |             |

