# Task List

1. ✅ Implement OrderService.findById(Long) and wire to OrderController GET /api/orders/{id}
Added findById and compiled
2. ⏳ Add order status transitions OUT_FOR_DELIVERY and DELIVERED with notifications

3. ⏳ Extend ReturnService to approve/reject/pickup and PaymentService to simulate failure/refund

4. 🔄 Resolve 403 for unauthenticated GET /api/shops via external route (CORS/proxy)
SecurityConfig permits GET; need to allow unauth and ensure no JWT requirement or 403 from missing Origin. Test external after run.
5. ⏳ Finalize prod profile docs and env-based Postgres config

6. 🔄 Define Caffeine caches/TTL and Redis-ready toggle
Added CacheConfig with TTL; Redis-ready pending
7. ⏳ Add optimistic locking retry with backoff for inventory reserve/release

8. ⏳ Seed demo user and document auth flow

9. ⏳ Basic tests for repositories, services, controllers

10. ⏳ Pin spring-boot-maven-plugin version to 3.3.3 and verify build/run

11. 🔄 Rebuild, run, and verify new Payment/Return endpoints; commit changes


