-- Cart table
CREATE TABLE Cart (
  id TEXT NOT NULL,
  temp_id TEXT DEFAULT NULL,
  PRIMARY KEY (id)
);

-- CartProduct table
CREATE TABLE CartProduct (
  cart_id TEXT NOT NULL,
  product_slug TEXT NOT NULL,
  quantity INTEGER NOT NULL DEFAULT 1,
  PRIMARY KEY (cart_id, product_slug),
  FOREIGN KEY (cart_id) REFERENCES Cart(id),
  FOREIGN KEY (product_slug) REFERENCES Product(url_slug)
);

-- Orders table
CREATE TABLE Orders (
  order_id INTEGER NOT NULL,
  user_id INTEGER,
  cart_id TEXT NOT NULL,
  createdDate TEXT NOT NULL,
  shippingStatus TEXT NOT NULL CHECK(shippingStatus IN ('PENDING', 'SHIPPED', 'DELIVERED')),
  shippingAddress TEXT,
  shipping_id INTEGER DEFAULT NULL,
  paymentMethod TEXT NOT NULL DEFAULT 'COD',
  PRIMARY KEY (order_id AUTOINCREMENT),
  FOREIGN KEY (user_id) REFERENCES Users(user_id),
  FOREIGN KEY (cart_id) REFERENCES Cart(id)
);

-- Product table
CREATE TABLE Product (
  id INTEGER NOT NULL,
  name TEXT NOT NULL,
  description TEXT,
  vendor TEXT DEFAULT NULL,
  url_slug TEXT NOT NULL,
  sku TEXT NOT NULL,
  price REAL NOT NULL,
  discount_percent INTEGER DEFAULT 0,
  is_featured INTEGER DEFAULT 0,
  is_delete INTEGER DEFAULT 0,
  PRIMARY KEY (id AUTOINCREMENT),
  UNIQUE (url_slug),
  UNIQUE (sku)
);

-- Role table
CREATE TABLE Role (
  role_id INTEGER NOT NULL,
  role_name TEXT NOT NULL,
  PRIMARY KEY (role_id AUTOINCREMENT),
  UNIQUE (role_name)
);

-- Users table
CREATE TABLE Users (
  user_id INTEGER NOT NULL,
  username TEXT NOT NULL,
  password TEXT NOT NULL,
  email TEXT NOT NULL,
  role_id INTEGER DEFAULT NULL,
  status TEXT DEFAULT NULL,
  cart_id TEXT DEFAULT NULL,
  PRIMARY KEY (user_id AUTOINCREMENT),
  UNIQUE (username),
  UNIQUE (email),
  FOREIGN KEY (role_id) REFERENCES Role(role_id)
);