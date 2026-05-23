# Tomato Platform - Open Issues

This document tracks 35 planned features not yet implemented. Each issue follows GitHub issue format.

---

## Search & Filters

### ISSUE-001: Search bar with live autocomplete

**Labels:** `enhancement`, `frontend`, `search`  
**Priority:** P0  
**Description:** Add a search bar in the navbar and home page hero that provides live autocomplete suggestions as the user types, matching restaurant names and cuisines.  
**Acceptance Criteria:**
- Search input visible on navbar and home hero
- Debounced API call (300ms) returns matching restaurants
- Dropdown shows name, cuisine, city for each match
- Clicking a result navigates to restaurant detail page
**Estimated effort:** 3 days

---

### ISSUE-002: Filter by cuisine, price, rating, delivery time

**Labels:** `enhancement`, `frontend`, `backend`, `filters`  
**Priority:** P1  
**Description:** Implement multi-faceted filters on the restaurants listing page allowing users to narrow results by cuisine type, price range, minimum rating, and estimated delivery time.  
**Acceptance Criteria:**
- Filter panel/sidebar on Restaurants page
- Backend supports query params for all filter dimensions
- Applied filters reflected in URL query string
- Clear all filters button resets to default view
**Estimated effort:** 4 days

---

### ISSUE-003: Sort restaurants by rating, delivery time, price

**Labels:** `enhancement`, `frontend`, `backend`  
**Priority:** P1  
**Description:** Add sort dropdown on restaurant listing allowing users to order results by rating (high-low), delivery time (fastest), or price (low-high).  
**Acceptance Criteria:**
- Sort dropdown with at least 3 options
- Backend `GET /api/restaurants` accepts `sort` query param
- Sort selection persists during session
- Default sort is by rating descending
**Estimated effort:** 2 days

---

### ISSUE-004: City/location selector in navbar

**Labels:** `enhancement`, `frontend`  
**Priority:** P0  
**Description:** Add a city selector in the navbar so users can choose their delivery location (Mumbai, Delhi, Bangalore) and see restaurants for that city.  
**Acceptance Criteria:**
- City dropdown in navbar with 3 cities
- Selected city stored in Zustand + localStorage
- Restaurant listings filter by selected city automatically
- City shown in hero tagline on home page
**Estimated effort:** 2 days

---

### ISSUE-005: Vegetarian-only toggle filter

**Labels:** `enhancement`, `frontend`, `backend`  
**Priority:** P1  
**Description:** Replace client-side Veg/Non-Veg tabs with a proper vegetarian filter backed by menu item `isVeg` field on the backend.  
**Acceptance Criteria:**
- `isVeg` boolean field added to MenuItem model
- Toggle filter on Restaurants page
- Only restaurants with veg items shown when enabled
- Seed data updated with veg flags
**Estimated effort:** 2 days

---

### ISSUE-006: "Open Now" filter

**Labels:** `enhancement`, `frontend`  
**Priority:** P2  
**Description:** Add a toggle filter to show only restaurants that are currently open (`isOpen = true`).  
**Acceptance Criteria:**
- Toggle on Restaurants page labeled "Open Now"
- Filters client-side or via `?isOpen=true` API param
- Closed restaurants hidden when filter active
- Badge count shows number of open restaurants
**Estimated effort:** 1 day

---

## Payment Gateway

### ISSUE-007: Checkout flow / Order placement API

**Labels:** `feature`, `backend`, `frontend`  
**Priority:** P0  
**Description:** Implement checkout flow allowing authenticated users to place orders from their cart. Create `POST /api/orders` endpoint.  
**Acceptance Criteria:**
- `POST /api/orders` creates order from current cart
- Cart cleared after successful order placement
- Order confirmation page shows order ID and summary
- "Proceed to Checkout" button enabled on Cart page
**Estimated effort:** 5 days

---

### ISSUE-008: Order model and OrderItem model

**Labels:** `feature`, `backend`, `database`  
**Priority:** P0  
**Description:** Create JPA entities for Order and OrderItem with relationships to User, Restaurant, and MenuItem.  
**Acceptance Criteria:**
- `Order` entity: id, user, restaurant, status, total, createdAt, deliveryAddress
- `OrderItem` entity: id, order, menuItem, quantity, price
- Order status enum: PLACED, CONFIRMED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
- Repositories and DTOs created
**Estimated effort:** 2 days

---

### ISSUE-009: Razorpay / Stripe integration

**Labels:** `feature`, `backend`, `integration`  
**Priority:** P0  
**Description:** Integrate Razorpay (or Stripe) payment gateway for processing order payments during checkout.  
**Acceptance Criteria:**
- Payment intent created on order placement
- Frontend payment modal/redirect works
- Webhook handler confirms payment success/failure
- Order status updated based on payment result
**Estimated effort:** 5 days

---

### ISSUE-010: Payment success/failure pages

**Labels:** `feature`, `frontend`  
**Priority:** P1  
**Description:** Create dedicated pages for payment success and failure outcomes with appropriate messaging and next steps.  
**Acceptance Criteria:**
- `/payment/success` page with order summary
- `/payment/failure` page with retry option
- Redirect from payment gateway to correct page
- Link to order tracking from success page
**Estimated effort:** 2 days

---

### ISSUE-011: Order history page

**Labels:** `feature`, `frontend`, `backend`  
**Priority:** P1  
**Description:** Implement `GET /api/orders/my` endpoint and a frontend page showing the user's past orders.  
**Acceptance Criteria:**
- Paginated order history API endpoint
- `/orders` page lists past orders with date, restaurant, total, status
- Click order opens detail view
- Protected route requiring authentication
**Estimated effort:** 3 days

---

### ISSUE-012: Admin order management

**Labels:** `feature`, `backend`, `admin`  
**Priority:** P1  
**Description:** Admin dashboard/API for viewing and managing all orders across restaurants.  
**Acceptance Criteria:**
- `GET /api/admin/orders` with filters by status, restaurant, date
- `PUT /api/admin/orders/{id}/status` to update order status
- ADMIN role required for all endpoints
- Basic admin UI or API documentation
**Estimated effort:** 4 days

---

## Order Tracking

### ISSUE-013: Real-time order status updates

**Labels:** `feature`, `backend`, `websocket`  
**Priority:** P1  
**Description:** Implement real-time order status updates using WebSocket or polling so users see live status changes.  
**Acceptance Criteria:**
- WebSocket endpoint `/ws/orders/{orderId}` or polling every 10s
- Frontend subscribes on order detail/tracking page
- Status changes reflected without page refresh
- Connection gracefully handles disconnects
**Estimated effort:** 4 days

---

### ISSUE-014: Order status lifecycle enum

**Labels:** `feature`, `backend`  
**Priority:** P0  
**Description:** Formalize order status state machine: PLACED → CONFIRMED → PREPARING → OUT_FOR_DELIVERY → DELIVERED.  
**Acceptance Criteria:**
- Status transitions validated (no skipping steps)
- Timestamps recorded for each status change
- Status history stored in `OrderStatusHistory` table
- Invalid transitions return 400 error
**Estimated effort:** 2 days

---

### ISSUE-015: Delivery agent assignment model

**Labels:** `feature`, `backend`, `database`  
**Priority:** P2  
**Description:** Create DeliveryAgent entity and assignment logic linking agents to orders for last-mile delivery.  
**Acceptance Criteria:**
- `DeliveryAgent` entity with name, phone, vehicle, currentLocation
- Agent assigned when order moves to OUT_FOR_DELIVERY
- Agent info visible on order tracking page
- Admin can manage agent roster
**Estimated effort:** 3 days

---

### ISSUE-016: Live map tracking UI component

**Labels:** `feature`, `frontend`  
**Priority:** P2  
**Description:** Build a map component showing delivery agent location and route to customer's address in real-time.  
**Acceptance Criteria:**
- Map integration (Google Maps / Mapbox)
- Agent marker updates in real-time
- ETA displayed based on distance
- Responsive on mobile devices
**Estimated effort:** 5 days

---

### ISSUE-017: Push notification on status change

**Labels:** `feature`, `frontend`, `notifications`  
**Priority:** P2  
**Description:** Show toast/browser notifications when order status changes during active tracking session.  
**Acceptance Criteria:**
- Toast appears on status change (e.g., "Your order is being prepared")
- Browser push notification permission requested
- Notifications work when tab is in background
- User can disable notifications in settings
**Estimated effort:** 2 days

---

## Reviews & Ratings

### ISSUE-018: Review model

**Labels:** `feature`, `backend`, `database`  
**Priority:** P1  
**Description:** Create Review entity linking User, Restaurant, rating (1-5), comment, and createdAt.  
**Acceptance Criteria:**
- `Review` JPA entity with all required fields
- One review per user per restaurant (unique constraint)
- Repository and DTO created
- Validation: rating 1-5, comment max 500 chars
**Estimated effort:** 1 day

---

### ISSUE-019: POST /api/restaurants/{id}/reviews

**Labels:** `feature`, `backend`  
**Priority:** P1  
**Description:** Allow authenticated users who have ordered from a restaurant to submit a review.  
**Acceptance Criteria:**
- POST endpoint creates review for authenticated user
- Duplicate review returns 409 Conflict
- Only users with completed orders can review
- Returns created review in standard ApiResponse format
**Estimated effort:** 2 days

---

### ISSUE-020: GET /api/restaurants/{id}/reviews (paginated)

**Labels:** `feature`, `backend`  
**Priority:** P1  
**Description:** Fetch paginated reviews for a restaurant to display on restaurant detail page.  
**Acceptance Criteria:**
- Paginated GET endpoint with `page` and `size` params
- Returns reviewer name, rating, comment, date
- Sorted by newest first
- Public endpoint (no auth required)
**Estimated effort:** 1 day

---

### ISSUE-021: Star rating UI component

**Labels:** `feature`, `frontend`  
**Priority:** P1  
**Description:** Build reusable star rating component for displaying and submitting ratings.  
**Acceptance Criteria:**
- `StarRating` component with display and input modes
- Half-star display support for averages
- Interactive hover state for input mode
- Used on restaurant detail and review form
**Estimated effort:** 1 day

---

### ISSUE-022: Average rating recalculation

**Labels:** `feature`, `backend`  
**Priority:** P2  
**Description:** Automatically recalculate restaurant average rating when a new review is submitted.  
**Acceptance Criteria:**
- Restaurant `rating` field updated on new review
- Calculation uses average of all review ratings
- Atomic update (no race conditions)
- Rating displayed with one decimal place
**Estimated effort:** 1 day

---

## Recommendations

### ISSUE-023: Order history-based recommendation engine

**Labels:** `feature`, `backend`, `ml`  
**Priority:** P2  
**Description:** Build a recommendation engine that suggests restaurants based on user's past order history and preferences.  
**Acceptance Criteria:**
- Service analyzes user's order history
- Returns top 5 recommended restaurants
- `GET /api/recommendations` endpoint for authenticated users
- Recommendations exclude already-frequented restaurants (variety)
**Estimated effort:** 5 days

---

### ISSUE-024: "Previously ordered" section on home page

**Labels:** `feature`, `frontend`  
**Priority:** P1  
**Description:** Show a "Order Again" section on the home page with restaurants the user has previously ordered from.  
**Acceptance Criteria:**
- Section visible only for logged-in users with order history
- Shows last 4 restaurants ordered from
- Quick reorder button on each card
- Hidden when user has no order history
**Estimated effort:** 2 days

---

### ISSUE-025: "People near you ordered" section

**Labels:** `feature`, `frontend`, `backend`  
**Priority:** P2  
**Description:** Display trending restaurants in the user's city based on recent orders from other users in the same area.  
**Acceptance Criteria:**
- Section on home page titled "People near you ordered"
- Based on order count in last 7 days per city
- Requires city selection (ISSUE-004)
- Shows top 6 restaurants
**Estimated effort:** 3 days

---

### ISSUE-026: Trending restaurants by city

**Labels:** `feature`, `backend`  
**Priority:** P2  
**Description:** API endpoint returning trending restaurants for a given city based on order volume and ratings.  
**Acceptance Criteria:**
- `GET /api/restaurants/trending?city=Mumbai` endpoint
- Algorithm weighs order count (70%) + rating (30%)
- Returns top 10 restaurants
- Cached for 1 hour
**Estimated effort:** 2 days

---

### ISSUE-027: Personalized homepage feed

**Labels:** `feature`, `frontend`, `backend`  
**Priority:** P2  
**Description:** Dynamic homepage that combines recommendations, trending, previously ordered, and featured into a personalized feed.  
**Acceptance Criteria:**
- Feed layout adapts to logged-in vs guest users
- Sections ordered by relevance score
- Loads within 2 seconds
- Fallback to featured restaurants for new users
**Estimated effort:** 4 days

---

### ISSUE-028: Email digest of recommended restaurants

**Labels:** `feature`, `backend`, `scheduler`  
**Priority:** P2  
**Description:** Weekly scheduled email sending personalized restaurant recommendations to opted-in users.  
**Acceptance Criteria:**
- Spring `@Scheduled` job runs weekly
- Email template with top 5 recommendations
- Users can opt out via preference setting
- Uses Spring Mail with configurable SMTP
**Estimated effort:** 3 days

---

## Frontend Polish

### ISSUE-029: Skeleton loading screens

**Labels:** `enhancement`, `frontend`, `ux`  
**Priority:** P1  
**Description:** Replace loading spinners with skeleton placeholder screens for restaurant cards and menu items.  
**Acceptance Criteria:**
- `RestaurantCardSkeleton` and `MenuItemCardSkeleton` components
- Shimmer animation effect
- Used on Home, Restaurants, and RestaurantDetail pages
- Matches final card layout dimensions
**Estimated effort:** 2 days

---

### ISSUE-030: Empty state illustrations

**Labels:** `enhancement`, `frontend`, `ux`  
**Priority:** P2  
**Description:** Add illustrated empty states for cart empty, no search results, no order history, and error states.  
**Acceptance Criteria:**
- SVG illustrations for each empty state
- Friendly copy with call-to-action button
- Used on Cart, Restaurants (no results), Orders pages
- Consistent illustration style
**Estimated effort:** 2 days

---

### ISSUE-031: Toast notification system

**Labels:** `enhancement`, `frontend`, `ux`  
**Priority:** P1  
**Description:** Implement a global toast notification system for success, error, and info messages across the app.  
**Acceptance Criteria:**
- Toast appears on add-to-cart, login, errors
- Auto-dismiss after 3 seconds
- Stack multiple toasts
- Accessible (ARIA live region)
**Estimated effort:** 1 day

---

### ISSUE-032: Onboarding flow (city selection modal)

**Labels:** `enhancement`, `frontend`, `ux`  
**Priority:** P2  
**Description:** First-time user onboarding modal prompting city selection before browsing restaurants.  
**Acceptance Criteria:**
- Modal shown on first visit (localStorage flag)
- City selection persisted
- Skip option available
- Never shown again after completion
**Estimated effort:** 1 day

---

### ISSUE-033: Dark mode toggle

**Labels:** `enhancement`, `frontend`, `ux`  
**Priority:** P2  
**Description:** Add dark mode support with toggle in navbar, respecting system preference by default.  
**Acceptance Criteria:**
- Toggle in navbar switches light/dark
- Preference saved in localStorage
- All pages and components support dark theme
- Tailwind `dark:` classes used throughout
**Estimated effort:** 3 days

---

### ISSUE-034: Infinite scroll / pagination on restaurant list

**Labels:** `enhancement`, `frontend`  
**Priority:** P1  
**Description:** Implement infinite scroll or load-more pagination on the restaurants listing page.  
**Acceptance Criteria:**
- Loads next page when user scrolls near bottom
- Loading indicator at bottom during fetch
- No duplicate restaurants across pages
- Works with active filters (ISSUE-002)
**Estimated effort:** 2 days

---

### ISSUE-035: Restaurant image gallery carousel

**Labels:** `enhancement`, `frontend`  
**Priority:** P2  
**Description:** Add an image gallery carousel on restaurant detail page showing multiple food and ambiance photos.  
**Acceptance Criteria:**
- Carousel with swipe/arrow navigation
- Multiple images per restaurant in database
- Thumbnail strip below main image
- Fullscreen view on image click
**Estimated effort:** 2 days
