FastFashion Backend - API Quickstart

Auth
- POST /api/auth/register {email,password,name,phone} -> {token}
- POST /api/auth/login {email,password} -> {token}

Public Catalog
- GET /api/shops?city=Bangalore
- GET /api/shops/{id}/products

Profile
- GET /api/me (Authorization: Bearer <token>)

Orders
- POST /api/orders (Bearer) {shopId, deliveryAddress, items:[{productId,quantity}]} -> Order
- POST /api/orders/{id}/cancel (Bearer)
- GET /api/orders/me (Bearer)
- GET /api/orders/{id} (Bearer)

Payments (simulated)
- POST /api/payments/{orderId} (Bearer) -> Payment
- POST /api/payments/{orderId}/fail (Bearer)
- POST /api/payments/{orderId}/refund (Bearer)

Returns
- POST /api/returns/{orderId} (Bearer)
- POST /api/returns/admin/{returnId}/approve (Bearer)
- POST /api/returns/admin/{returnId}/reject?reason=... (Bearer)
- POST /api/returns/admin/{returnId}/pickup (Bearer)

SSE Notifications
- GET /api/notifications/subscribe -> text/event-stream
  Notes: Anonymous clients subscribe to channel 0L; authenticated users subscribe to their user-specific channel.

CORS
- Configured via app.cors.*; OPTIONS preflight permitted globally.

Caching
- Default: Caffeine with TTL app.cache.ttl-seconds (default 300s); caches: shops, productsByShop
- Enable Redis: add spring.data.redis.host (and port/password). When present, RedisCacheManager is used automatically.

Demo Credentials
- demo@fastfashion.local / password
- merchant@fastfashion.local / password

Run (dev)
- mvn spring-boot:run

Run (prod)
- export SPRING_PROFILES_ACTIVE=prod
- export DB_URL=jdbc:postgresql://host:5432/fastfashion; DB_USER=...; DB_PASSWORD=...
- export JWT_SECRET=... (32+ chars)
- java -jar target/fastfashion-0.0.1-SNAPSHOT.jar

Curl Examples
1) Login
curl -sX POST http://localhost:12000/api/auth/login -H 'Content-Type: application/json' \
  -d '{"email":"demo@fastfashion.local","password":"password"}' | jq -r .token

2) List shops (public)
curl -s 'http://localhost:12000/api/shops?city=Bangalore'

3) Place order
TOKEN=...
SHOP_ID=$(curl -s http://localhost:12000/api/shops | jq -r '.[0].id')
PID=$(curl -s http://localhost:12000/api/shops/$SHOP_ID/products | jq -r '.[0].id')
curl -sX POST http://localhost:12000/api/orders -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' \
  -d '{"shopId":'"$SHOP_ID"',"deliveryAddress":"Koramangala","items":[{"productId":'"$PID"',"quantity":1}]}'

4) Subscribe SSE
curl -N http://localhost:12000/api/notifications/subscribe -H "Authorization: Bearer $TOKEN"
