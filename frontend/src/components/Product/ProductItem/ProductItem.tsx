import { Text, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import type { Product } from '@/types/product';

interface ProductItemProps {
  product: Product;
}

const ProductItem = ({ product }: ProductItemProps) => {
  const theme = useTheme();
  const { name, price, image, averageRating, reviewCount } = product;

  return (
    <ProductItemContainer>
      <img src={image} width={90} height={90} alt={name} />
      <ProductInfoWrapper>
        <Text size="lg" weight="bold">
          {name}
        </Text>
        <Text color={theme.textColors.info}>{price.toLocaleString('ko-KR')}원</Text>
        <ProductReviewWrapper>
          <RatingIconWrapper>
            <SvgIcon variant="star" width={20} height={20} color={theme.colors.secondary} />
            <Text as="span" css="line-height: 24px;">
              {averageRating}
            </Text>
          </RatingIconWrapper>
          <ReviewIconWrapper>
            <SvgIcon variant="review" width={20} height={20} color={theme.colors.gray5} />
            <Text as="span" css="line-height: 24px">
              {reviewCount}
            </Text>
          </ReviewIconWrapper>
        </ProductReviewWrapper>
      </ProductInfoWrapper>
    </ProductItemContainer>
  );
};

export default ProductItem;

const ProductItemContainer = styled.div`
  display: flex;
  align-items: center;
  height: 150px;
  padding: 30px;
`;

const ProductInfoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
  margin-left: 30px;
`;

const ProductReviewWrapper = styled.div`
  display: flex;
  column-gap: 20px;
  margin-left: -2px;
`;

const RatingIconWrapper = styled.div`
  display: flex;
  align-items: center;
  column-gap: 4px;

  & > svg {
    padding-bottom: 2px;
  }
`;

const ReviewIconWrapper = styled.div`
  display: flex;
  align-items: center;
  column-gap: 4px;

  & > svg {
    padding-top: 2px;
  }
`;