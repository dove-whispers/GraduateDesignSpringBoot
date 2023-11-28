# GraduateDesignSpringBoot
## 本科毕业设计 - 基于Spring Boot的报销单管理系统设计与实现
[github地址](https://github.com/dove-whispers/GraduateDesignSpringBoot)
  1. 本系统基于 Spring Boot 框架设计并实现了报销单管理系统，以期满足当前信息化时代以及后疫情时代下方便快捷管理、审核、修改、报销电子报销单等一系列流程的需求。首先明确系统用户，针对目标用户进行需求调研，明确系统需求。其次参考现存的一些后台管理系统，结合实际情况，合理设计系统功能。本系统使用 Spring Boot 框架作为后端框架，使用结构化设计，提高代码质量以及可维护性，并降低耦合性；使用 MySQL 作为数据库，保证数据存的稳定性和安全性；使用 Thymeleaf 作为前端模板引擎，jQuery 和 Bootstrap 作为前端框架，简化开发，提升开发效率，同时也保证了前端代码的规范和质量。
  2. 本系统的使用者有着众多的身份，每种身份的用户所能看到的内容和所能进行的操作都不尽相同 **(暂时停用)**。同时也要为他们进行细致的权限控制和管理，搞清楚每种角色在整个业务逻辑中的工作，也要在为他们提供统一接口的基础上进行更能进一步的详细判断，以保证系统正确稳定地运行。而且对应于报销单的处理流程的不同步骤，报销单在每一步也有着不同的报销单状态，也该为每一张报销单的状态以及所处步骤进行展示，给用户提供良好的 UI 指导。
  3. 本系统在编程时，进行了详细的注释，提升了系统的可读性，方便日后根据不同商家的具体要求，进行相关的调整，同时也基于 swagger 在代码中编写了相关注解，可以自动生成相应的接口文档。但由于个人开发能力的局限性，系统页面的美观性及一些功能性方面没能做到尽善尽美，还有可以不断修改完善提升的地方，希望在今后的学习中能努力提升自己的专业技术，对本系统进行优化完善。
