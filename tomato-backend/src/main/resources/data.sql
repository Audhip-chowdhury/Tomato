-- Users (BCrypt hashed passwords)
INSERT INTO users (id, name, email, password, role, phone) VALUES
(1, 'Admin User', 'admin@tomato.com', '$2b$10$9rR26eE2MaVsGUWgUdwtwORwVxfIdrIb7YKGeXI7Xo6IIIpgJSgha', 'ADMIN', '9876543210'),
(2, 'Regular User', 'user@tomato.com', '$2b$10$4.35e/bXD6X5.CF1Sjea3uAkUdMkj7pEqaiFeXLV9IVysL/cQ5alG', 'USER', '9876543211');

-- Restaurants (10 across Mumbai, Delhi, Bangalore)
INSERT INTO restaurants (id, name, cuisine, city, rating, image_url, is_open) VALUES
(1, 'Punjab Grill', 'North Indian', 'Mumbai', 4.5, 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=400', true),
(2, 'Saravana Bhavan', 'South Indian', 'Mumbai', 4.3, 'https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=400', true),
(3, 'Wok Express', 'Chinese', 'Mumbai', 4.1, 'https://images.unsplash.com/photo-1559339352-11d035aa65de?w=400', true),
(4, 'Karim''s', 'North Indian', 'Delhi', 4.6, 'https://images.unsplash.com/photo-1552566626-52f8b828add9?w=400', true),
(5, 'Biryani Blues', 'Biryani', 'Delhi', 4.4, 'https://images.unsplash.com/photo-1563379091339-03246963d96c?w=400', true),
(6, 'Pizza Hut', 'Pizza', 'Delhi', 4.0, 'https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400', true),
(7, 'Truffles', 'Burgers', 'Bangalore', 4.7, 'https://images.unsplash.com/photo-1571091718767-18b5b1457a45?w=400', true),
(8, 'MTR', 'South Indian', 'Bangalore', 4.5, 'https://images.unsplash.com/photo-1589302168068-964664d93aa0?w=400', true),
(9, 'Corner House', 'Desserts', 'Bangalore', 4.8, 'https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=400', true),
(10, 'Mainland China', 'Chinese', 'Bangalore', 4.2, 'https://images.unsplash.com/photo-1551218808-94e220e084d2?w=400', false);

-- Menu Items (40 total, 4 per restaurant)
INSERT INTO menu_items (id, restaurant_id, name, description, price, category, image_url) VALUES
-- Punjab Grill (1)
(1, 1, 'Butter Chicken', 'Creamy tomato-based curry with tender chicken', 349.00, 'Main Course', 'https://images.unsplash.com/photo-1603894584371-3e5e4e7c8f3a?w=300'),
(2, 1, 'Dal Makhani', 'Slow-cooked black lentils in rich cream', 249.00, 'Main Course', 'https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=300'),
(3, 1, 'Garlic Naan', 'Soft leavened bread with garlic butter', 59.00, 'Breads', 'https://images.unsplash.com/photo-1601050690597-df9a8d9b3e1e?w=300'),
(4, 1, 'Gulab Jamun', 'Deep-fried milk dumplings in sugar syrup', 99.00, 'Desserts', 'https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=300'),
-- Saravana Bhavan (2)
(5, 2, 'Masala Dosa', 'Crispy crepe filled with spiced potato', 120.00, 'Breakfast', 'https://images.unsplash.com/photo-1631452180519-c014fe946588?w=300'),
(6, 2, 'Idli Sambar', 'Steamed rice cakes with lentil stew', 80.00, 'Breakfast', 'https://images.unsplash.com/photo-1589302168068-964664d93aa0?w=300'),
(7, 2, 'Filter Coffee', 'Traditional South Indian coffee', 40.00, 'Beverages', 'https://images.unsplash.com/photo-1514432353612-6e0d781e2dd3?w=300'),
(8, 2, 'Medu Vada', 'Crispy lentil donuts served with chutney', 70.00, 'Snacks', 'https://images.unsplash.com/photo-1606491956689-2ea3a0c0e0c0?w=300'),
-- Wok Express (3)
(9, 3, 'Hakka Noodles', 'Stir-fried noodles with vegetables', 199.00, 'Noodles', 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=300'),
(10, 3, 'Manchurian', 'Crispy vegetable balls in tangy sauce', 179.00, 'Starters', 'https://images.unsplash.com/photo-1525755662778-4d77b060b0d0?w=300'),
(11, 3, 'Fried Rice', 'Wok-tossed rice with mixed vegetables', 189.00, 'Rice', 'https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=300'),
(12, 3, 'Spring Rolls', 'Crispy rolls with vegetable filling', 129.00, 'Starters', 'https://images.unsplash.com/photo-1526318472351-c5fcf85b2e0e?w=300'),
-- Karim's (4)
(13, 4, 'Mutton Korma', 'Slow-cooked mutton in aromatic gravy', 399.00, 'Main Course', 'https://images.unsplash.com/photo-1603894584371-3e5e4e7c8f3a?w=300'),
(14, 4, 'Chicken Tikka', 'Char-grilled marinated chicken pieces', 299.00, 'Starters', 'https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=300'),
(15, 4, 'Roomali Roti', 'Thin hand-tossed flatbread', 45.00, 'Breads', 'https://images.unsplash.com/photo-1601050690597-df9a8d9b3e1e?w=300'),
(16, 4, 'Phirni', 'Creamy rice pudding with cardamom', 89.00, 'Desserts', 'https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=300'),
-- Biryani Blues (5)
(17, 5, 'Chicken Biryani', 'Fragrant basmati rice with spiced chicken', 299.00, 'Biryani', 'https://images.unsplash.com/photo-1563379091339-03246963d96c?w=300'),
(18, 5, 'Mutton Biryani', 'Slow-cooked mutton layered with rice', 399.00, 'Biryani', 'https://images.unsplash.com/photo-1589302168068-964664d93aa0?w=300'),
(19, 5, 'Raita', 'Cool yogurt with cucumber and mint', 49.00, 'Sides', 'https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=300'),
(20, 5, 'Keema Pav', 'Spiced minced meat with buttered buns', 199.00, 'Snacks', 'https://images.unsplash.com/photo-1571091718767-18b5b1457a45?w=300'),
-- Pizza Hut (6)
(21, 6, 'Margherita Pizza', 'Classic tomato and mozzarella pizza', 299.00, 'Pizza', 'https://images.unsplash.com/photo-1513104890138-7c749659a591?w=300'),
(22, 6, 'Pepperoni Pizza', 'Spicy pepperoni with melted cheese', 399.00, 'Pizza', 'https://images.unsplash.com/photo-1628840042765-356cda07504e?w=300'),
(23, 6, 'Garlic Bread', 'Toasted bread with garlic butter', 149.00, 'Sides', 'https://images.unsplash.com/photo-1601050690597-df9a8d9b3e1e?w=300'),
(24, 6, 'Chocolate Brownie', 'Warm brownie with vanilla ice cream', 179.00, 'Desserts', 'https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=300'),
-- Truffles (7)
(25, 7, 'All American Burger', 'Beef patty with cheese, lettuce and tomato', 299.00, 'Burgers', 'https://images.unsplash.com/photo-1571091718767-18b5b1457a45?w=300'),
(26, 7, 'Veggie Burger', 'Plant-based patty with fresh toppings', 249.00, 'Burgers', 'https://images.unsplash.com/photo-1520072959219-c595d6d5ac08?w=300'),
(27, 7, 'Loaded Fries', 'Crispy fries with cheese and jalapenos', 149.00, 'Sides', 'https://images.unsplash.com/photo-1573080496219-b080a9451740?w=300'),
(28, 7, 'Milkshake', 'Thick chocolate milkshake', 129.00, 'Beverages', 'https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=300'),
-- MTR (8)
(29, 8, 'Rava Idli', 'Semolina steamed cakes with coconut chutney', 90.00, 'Breakfast', 'https://images.unsplash.com/photo-1589302168068-964664d93aa0?w=300'),
(30, 8, 'Bisi Bele Bath', 'Spicy rice and lentil one-pot meal', 150.00, 'Main Course', 'https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=300'),
(31, 8, 'Rasam', 'Tangy South Indian pepper soup', 60.00, 'Soups', 'https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=300'),
(32, 8, 'Payasam', 'Sweet vermicelli pudding with nuts', 80.00, 'Desserts', 'https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=300'),
-- Corner House (9)
(33, 9, 'Death by Chocolate', 'Rich chocolate ice cream sundae', 199.00, 'Ice Cream', 'https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=300'),
(34, 9, 'Hot Chocolate Fudge', 'Warm brownie with chocolate sauce', 179.00, 'Desserts', 'https://images.unsplash.com/photo-1606313564200-e75d5e30476e?w=300'),
(35, 9, 'Fruit Salad', 'Fresh seasonal fruits with cream', 129.00, 'Desserts', 'https://images.unsplash.com/photo-1564093497595-593b96d9f1a0?w=300'),
(36, 9, 'Mango Smoothie', 'Fresh mango blended smoothie', 149.00, 'Beverages', 'https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=300'),
-- Mainland China (10)
(37, 10, 'Dim Sum Platter', 'Assorted steamed dumplings', 399.00, 'Starters', 'https://images.unsplash.com/photo-1526318472351-c5fcf85b2e0e?w=300'),
(38, 10, 'Kung Pao Chicken', 'Spicy stir-fried chicken with peanuts', 349.00, 'Main Course', 'https://images.unsplash.com/photo-1603894584371-3e5e4e7c8f3a?w=300'),
(39, 10, 'Sweet Corn Soup', 'Creamy corn soup with vegetables', 149.00, 'Soups', 'https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=300'),
(40, 10, 'Honey Chilli Potato', 'Crispy potatoes in sweet chilli glaze', 199.00, 'Starters', 'https://images.unsplash.com/photo-1573080496219-b080a9451740?w=300');
