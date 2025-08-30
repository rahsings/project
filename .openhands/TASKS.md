# Task List

1. ✅ Implement OrderService.findById(Long) and wire to OrderController GET /api/orders/{id}

2. ✅ Add order status transitions OUT_FOR_DELIVERY and DELIVERED with notifications

3. ✅ Extend ReturnService to approve/reject/pickup and PaymentService to simulate failure/refund

4. 🔄 Resolve 403 for unauthenticated GET /api/shops via external route (CORS/proxy)
SecurityConfig permits GET; need to test external proxy with Origin and without token and evaluate responses
5. ✅ Finalize prod profile docs and env-based Postgres config

6. 🔄 Define Caffeine caches/TTL and Redis-ready toggle
Caffeine CacheManager implemented with TTL; Redis-ready note pending
7. ✅ Add optimistic locking retry with backoff for inventory reserve/release

8. ✅ Seed demo user and document auth flow

9. ⏳ Basic tests for repositories, services, controllers

10. ✅ Pin spring-boot-maven-plugin version to 3.3.3 and verify build/run

11. ✅ Rebuild, run, and verify new Payment/Return endpoints; commit changes

12. ✅ Add /api/orders/me to list current user's orders

13. ⏳ Run application on 12000 and verify unauthenticated GETs and CORS headers
Local environment lacks mvn/java; provide instructions and curl examples for user to validate

