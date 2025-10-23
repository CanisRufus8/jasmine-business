import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { ProductService } from "../../misc/ProductService";
import { Lightbox } from "./Lightbox";
import { ProductInfo } from "./ProductInfo";
import "./styles/css/styles.min.css";

export const ProductDetail = () => {
  const navigate = useNavigate();
  const { itemId } = useParams();
  const [images, setImages] = useState([]);
  const [productItem, setProductItem] = useState({});
  const [productImageList, setProductImageList] = useState([]);
  const [currentImageIndex, setCurrentImageIndex] = useState(0);
  const [lightbox, setLightbox] = useState(false);

  const [productQuantity, setProductQuantity] = useState(1);
  const [cartProductQuantity, setCartProductQuantity] = useState(0);

  const convertToVND = (price) => {
    if (!price) {
      price = 0;
    }
    return price.toLocaleString("vi-VN", {
      style: "currency",
      currency: "VND",
    });
  };

  const handleAddToCart = () => {
    setCartProductQuantity((prevState) => prevState + productQuantity);
    setProductQuantity(0);
  };

  useEffect(() => {
    if (itemId) {
      ProductService.getProductById(itemId)
        .then((res) => {
          setProductItem(res.data);
          setImages(res.data.productImages);
          const firstImage = { imageId: 0, imageURL: res.data.avatarURL };
          let images = res.data.productImages;
          images.unshift(firstImage);
          setProductImageList(images);
        })
        .catch((err1) => {
          console.log(err1);
          navigate("/");
        });
    }
  }, [itemId]);

  return (
    <>
      <main className="product">
        <div className="container-md grid product-container">
          <div className="flex product-image">
            <div className="center image-show">
              <img
                className="product-img"
                onClick={() => window.innerWidth > 768 && setLightbox(true)}
                src={
                  productImageList.length > 0 &&
                  productImageList[currentImageIndex].imageURL
                }
                alt=""
              />
            </div>
            <div className="thumbnail-wrapper flex center">
              {productImageList.length > 0 &&
                productImageList.slice(0, 6).map((item, index) => (
                  <div className="thumbnail" key={index}>
                    <img
                      onClick={() => setCurrentImageIndex(index)}
                      className={
                        currentImageIndex === index
                          ? "product-img active"
                          : "product-img"
                      }
                      src={productImageList[index].imageURL}
                      alt="thumbnail"
                    />
                  </div>
                ))}
            </div>

            {window.innerWidth <= 768 && (
              <>
                <button
                  onClick={() =>
                    setCurrentImageIndex((prevState) =>
                      prevState === 0
                        ? productImageList.length - 1
                        : prevState - 1
                    )
                  }
                  className="lightbox-control control-prev"
                >
                  <svg
                    width="13"
                    height="18"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      d="M11 1 3 9l8 8"
                      stroke="#1D2026"
                      strokeWidth="3"
                      fill="none"
                      fillRule="evenodd"
                    />
                  </svg>
                </button>
                <button
                  onClick={() =>
                    setCurrentImageIndex((prevState) =>
                      prevState === productImageList.length - 1
                        ? 0
                        : prevState + 1
                    )
                  }
                  className="lightbox-control control-next"
                >
                  <svg
                    width="13"
                    height="18"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      d="m2 1 8 8-8 8"
                      stroke="#1D2026"
                      strokeWidth="3"
                      fill="none"
                      fillRule="evenodd"
                    />
                  </svg>
                </button>
              </>
            )}
          </div>
          <div className="product-description flow">
            <p className="text-uppercase fs-500 fw-700 letter-spacing-1 title-color">
              Silva Farm's
            </p>
            <h1 className="fw-700 line-height-300 fs-800 blue">
              {productItem.name}
            </h1>
            <div className="product-title">
              <p className="fw-400 line-height-500 fs-400 darkGrayishBlue">
                {productItem.title}
              </p>
            </div>
            <div className="product-price">
              <div className="discounted-price flex">
                <span className="fw-700 blue fs-700">
                  {convertToVND(productItem.salePrice)}
                </span>
                <span className="offer fw-700 fs-400 title-color">
                  Discount {productItem.discountPercent} %
                </span>
              </div>
              <div className="original-price">
                <span className="fw-700 fs-400 line-height-500 text-line-through GrayishBlue">
                  {convertToVND(productItem.retailPrice)}
                </span>
              </div>
            </div>

            <div className="action-wrapper flex">
              <div className="product-quantity flex">
                <i
                  className="fa-solid fa-minus"
                  onClick={() =>
                    setProductQuantity((prevState) =>
                      prevState !== 0 ? prevState - 1 : 0
                    )
                  }
                />
                <span className="fw-700 fs-400 blue">{productQuantity}</span>
                <i
                  className="fa-solid fa-plus"
                  onClick={() =>
                    setProductQuantity((prevState) => prevState + 1)
                  }
                />
              </div>
              <button
                onClick={handleAddToCart}
                className="add-to-cart-btn flex fw-700 fs-400"
              >
                <svg width="22" height="20" xmlns="http://www.w3.org/2000/svg">
                  <path
                    d="M20.925 3.641H3.863L3.61.816A.896.896 0 0 0 2.717 0H.897a.896.896 0 1 0 0 1.792h1l1.031 11.483c.073.828.52 1.726 1.291 2.336C2.83 17.385 4.099 20 6.359 20c1.875 0 3.197-1.87 2.554-3.642h4.905c-.642 1.77.677 3.642 2.555 3.642a2.72 2.72 0 0 0 2.717-2.717 2.72 2.72 0 0 0-2.717-2.717H6.365c-.681 0-1.274-.41-1.53-1.009l14.321-.842a.896.896 0 0 0 .817-.677l1.821-7.283a.897.897 0 0 0-.87-1.114ZM6.358 18.208a.926.926 0 0 1 0-1.85.926.926 0 0 1 0 1.85Zm10.015 0a.926.926 0 0 1 0-1.85.926.926 0 0 1 0 1.85Zm2.021-7.243-13.8.81-.57-6.341h15.753l-1.383 5.53Z"
                    fill="#ffffff"
                    fillRule="nonzero"
                  />
                </svg>
                Add to cart
              </button>
            </div>
          </div>
        </div>

        {lightbox && (
          <Lightbox
            productData={productImageList.slice(0, 6)}
            setLightbox={setLightbox}
          />
        )}
      </main>

      <ProductInfo
        name={productItem.name}
        images={images}
        info={productItem.information}
        description={productItem.description}
        note={productItem.note}
      />
    </>
  );
};
