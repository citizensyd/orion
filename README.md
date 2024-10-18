# orion

## 📍 Overview

**Context**

You've joined the development team at ORION, a company specializing in software development, to work on a new social network, "Monde de Dév" (MDD). The aim of this network is to facilitate the networking of developers seeking employment, by encouraging exchanges and collaboration. Before its public launch, a minimum viable product (MVP) will be tested internally to fine-tune the application.

## 🚀 Getting started

***Dependencies***

Back :
- Java openjdk-21 java version "21.0.2'
- Maven 3.9.6
    
Front :
- node.js v18.17.1
- npm 10.2.3
- Angular CLI: 15.2.0
### 🔧 Installation

1. Cloning the orion repository:
```sh
git clone https://github.com/citizensyd/orion
```
For the back end
2. Go to project home directory:
```sh
cd Back
```

3. Installing dependencies:
```sh
mvn install
```
4. Add a dev.env file with your definitions like this :
```
DATABASE_URL=jdbc:mysql://localhost:3306/your_database?useSSL=false&serverTimezone=UTC
DATABASE_USERNAME=your_database_name
DATABASE_PASSWORD=your_password
JWT_KEY=your_sha_256_key
```
5. launch the back end
```sh
mvn spring-boot:run
```
For the front end
2. Go to project home directory:
```sh
cd Front
```

3. Installing dependencies:
```sh
nmp install
```
4. Launch the front end
```sh
ng serve
```
---
## 💼 Documentation Back end
After launching the back end you can test it at this address:

http://localhost:3001/swagger-ui/index.html

---

## 🤝 Contributing

Contributions are welcome! Here are several ways you can contribute:

- **[Submit Pull Requests](https://github.com/citizensyd/telesport/blob/main/CONTRIBUTING.md)**: Review open PRs, and submit your own PRs.
- **[Join the Discussions](https://github.com/citizensyd/telesport/discussions)**: Share your insights, provide feedback, or ask questions.
- **[Report Issues](https://github.com/citizensyd/telesport/issues)**: Submit bugs found or log feature requests for CITIZENSYD.

#### *Contributing Guidelines*

<details closed>
<summary>Click to expand</summary>

1. **Fork the Repository**: Start by forking the project repository to your GitHub account.
2. **Clone Locally**: Clone the forked repository to your local machine using a Git client.
   ```sh
   git clone <your-forked-repo-url>
   ```
3. **Create a New Branch**: Always work on a new branch, giving it a descriptive name.
   ```sh
   git checkout -b new-feature-x
   ```
4. **Make Your Changes**: Develop and test your changes locally.
5. **Commit Your Changes**: Commit with a clear and concise message describing your updates.
   ```sh
   git commit -m 'Implemented new feature x.'
   ```
6. **Push to GitHub**: Push the changes to your forked repository.
   ```sh
   git push origin new-feature-x
   ```
7. **Submit a Pull Request**: Create a PR against the original project repository. Clearly describe the changes and their motivations.

Once your PR is reviewed and approved, it will be merged into the main branch.

</details>
---

## 📄 License


- This project is protected by the MIT license. For more details, see the LICENSE file.
---

## 👏 Acknowledgments

- Special thanks to my mentor, Hichem, for his invaluable advice and support.
