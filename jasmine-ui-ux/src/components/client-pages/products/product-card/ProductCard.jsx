import React from "react";
import "./ProductCard.css"; // Tạo một file CSS riêng cho component này
import { Link } from "react-router-dom";
const ProductCard = ({
  id,
  name,
  avatarURL,
  title,
  retailPrice,
  discountPercent,
  salePrice,
}) => {
  const convertToVND = (price) => {
    if (!price) {
      price = 0;
    }
    return price.toLocaleString("vi-VN", {
      style: "currency",
      currency: "VND",
    });
  };

  return (
    <div className="product-card">
      <div>
        <Link as={Link} to={`/products/detail/${id}`}>
          <img src={avatarURL} alt={name} className="product_image" />
        </Link>
      </div>
      <div className="product_content">
        <div className="product_name">
          <strong title={name}>{name}</strong>
        </div>
        <div className="product_description">
          <span title={title} className="text-dark">
            {title}
          </span>
        </div>
        <div className="product_price">
          <div className="main_price">
            <s>{convertToVND(retailPrice)}</s>
          </div>
          <div className="sale_price">
            <strong className="text-success">{convertToVND(salePrice)}</strong>
          </div>
        </div>

        <div className="product_note">
          <span>
            {" "}
            <strong className="text-success">
              <span>Giá tốt - giao hàng nhanh </span>
              <i class="fa-solid fa-check"></i>
            </strong>
          </span>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
