

# Project Name

Order tracking

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## Pre-Installation
Make Sure Database has this two roles before creating any user or before running the app

INSERT INTO ft_ordertracking_schema.roles(name) VALUES('ROLE_USER');
INSERT INTO ft_ordertracking_schema.roles(name) VALUES('ROLE_ADMIN');

## Installation


```bash
# Clone the repository
git clone https://github.com/omor-faruque/ft-order-tracking.git

# Navigate to the project directory
cd your-project

# Install dependencies
mvn install
```

## Usage



```bash
# Run the application
mvn spring-boot:run
```

## Configuration


```yaml
# Example configuration file
server:
  port: 8080
```

## Contributing



## License

Specify the license under which your project is released. Include any licensing information and terms of use.

```
MIT License
```

