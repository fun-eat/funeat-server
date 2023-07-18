import { Button, Text, theme } from '@fun-eat/design-system';
import { useState } from 'react';
import type { CSSProp } from 'styled-components';
import styled from 'styled-components';

import type { Category } from '@/types/common';

interface CategoryMenuProps {
  menuVariant: 'food' | 'store';
  menuList: Category[];
}

const CategoryMenu = ({ menuList, menuVariant }: CategoryMenuProps) => {
  const [selectedMenu, setSelectedMenu] = useState(0);

  const selectMenu = (menuId: number) => {
    setSelectedMenu(menuId);
  };

  return (
    <CategoryMenuContainer>
      {menuList.map((menu) => {
        const isSelected = menu.id === selectedMenu;
        return (
          <li key={menu.id}>
            <Button
              type="button"
              color={isSelected ? 'white' : 'gray4'}
              variant={isSelected ? 'filled' : 'outlined'}
              size="xs"
              css={isSelected && selectedCategoryMenuStyles[menuVariant]}
              onClick={() => selectMenu(menu.id)}
            >
              <CategoryMenuButtonText size="xs" weight="bold" color={theme.textColors.sub}>
                {menu.name}
              </CategoryMenuButtonText>
            </Button>
          </li>
        );
      })}
    </CategoryMenuContainer>
  );
};

export default CategoryMenu;

type CategoryMenuStyleProps = Pick<CategoryMenuProps, 'menuVariant'>;

const CategoryMenuContainer = styled.ul`
  display: flex;
  gap: 8px;
`;

const selectedCategoryMenuStyles: Record<CategoryMenuStyleProps['menuVariant'], CSSProp> = {
  food: `
    background: ${theme.colors.gray5};
    color: ${theme.textColors.white};
  `,
  store: `
    background: ${theme.colors.primary};
    color: ${theme.textColors.default};
  `,
};

const CategoryMenuButtonText = styled(Text)`
  color: inherit;
`;