# Task List

1. ✅ Implement OrderService.findById(Long) and wire to OrderController GET /api/orders/{id}

2. ✅ Add order status transitions OUT_FOR_DELIVERY and DELIVERED with notifications

3. ✅ Extend ReturnService to approve/reject/pickup and PaymentService to simulate failure/refund

4. 🔄 Resolve 403 for unauthenticated GET /api/shops via external route (CORS/proxy)
Permitted OPTIONS preflight globally. Next: verify via curl with -H Origin & -H Access-Control-Request-Method once app is running externally.
5. ✅ Finalize prod profile docs and env-based Postgres config

6. 🔄 Define Caffeine caches/TTL and Redis-ready toggle
Caffeine is default. Next: add conditional Redis CacheManager when spring.data.redis.host is present, without introducing new deps unless user approves.
7. ✅ Add optimistic locking retry with backoff for inventory reserve/release

8. ✅ Seed demo user and document auth flow

9. ⏳ Basic tests for repositories, services, controllers

10. ✅ Pin spring-boot-maven-plugin version to 3.3.3 and verify build/run

11. ✅ Rebuild, run, and verify new Payment/Return endpoints; commit changes

12. ✅ Add /api/orders/me to list current user's orders

13. ⏳ Run application on 12000 and verify unauthenticated GETs and CORS headers
Provide run steps; runtime lacks Java/Maven here. User to run locally or in container.
14. ✅ Add API_DOCS.md with curl examples and demo creds


