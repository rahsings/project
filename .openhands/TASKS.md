# Task List

1. ✅ Initialize Spring Boot project (Maven), setup dependencies and base structure

2. ✅ Implement domain model: User, Shop, Product, Inventory, Order, OrderItem, Payment, ReturnRequest and enums

3. 🔄 Configure persistence (H2 dev, Postgres prod) and JPA repositories
Repositories split into individual files; H2 runtime enabled. Need application-prod.yaml for Postgres and profile switching.
4. ✅ Implement JWT authentication (register/login) and security configuration

5. 🔄 Implement core APIs: Shops, Products, Orders (place, status, cancel), Payments (simulate), Returns
Orders place/cancel implemented; profile endpoint added. Payments and returns pending.
6. 🔄 Add stock reservation with optimistic locking and cancellation workflow on inventory conflicts
Optimistic locking field on Inventory and reserve/release implemented. Need conflict retry/compensation and cancellation notification.
7. 🔄 Add caching for read-heavy endpoints (shops/products) with Caffeine; Redis-ready configuration
spring-boot-starter-cache + caffeine added; spring.cache.type=caffeine set. Redis-ready config pending.
8. ✅ Implement SSE endpoint for real-time order status updates and notification service

9. ✅ CORS and server configuration for port 12000 and 0.0.0.0

10. 🔄 Seed sample data and provide API usage examples
DataSeeder in place; consider seeding a demo user.
11. 🔄 Build, run, and verify service endpoints
App running on port 12000. Next: hit endpoints.
12. ✅ Initialize git repo and commit code


