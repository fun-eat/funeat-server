import { Button, Heading, Spacing, Text } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import type { MouseEventHandler } from 'react';
import { Suspense, useEffect, useState } from 'react';
import styled from 'styled-components';

import { ErrorBoundary, ErrorComponent, Input, Loading, SvgIcon, TabMenu } from '@/components/Common';
import { RecommendList, ProductSearchResultList, RecipeSearchResultList } from '@/components/Search';
import { SEARCH_PAGE_TABS } from '@/constants';
import { useDebounce } from '@/hooks/common';
import { useSearch } from '@/hooks/search';

const isProductSearchTab = (tabMenu: string) => tabMenu === SEARCH_PAGE_TABS[0];
const getInputPlaceholder = (tabMenu: string) =>
  isProductSearchTab(tabMenu) ? '상품 이름을 검색해보세요.' : '꿀조합에 포함된 상품을 입력해보세요.';

const SearchPage = () => {
  const {
    inputRef,
    searchQuery,
    isSubmitted,
    isAutocompleteOpen,
    handleSearchQuery,
    handleSearch,
    handleSearchClick,
    handleAutocompleteClose,
  } = useSearch();
  const [debouncedSearchQuery, setDebouncedSearchQuery] = useState(searchQuery || '');
  const [selectedTabMenu, setSelectedTabMenu] = useState<string>(SEARCH_PAGE_TABS[0]);
  const { reset } = useQueryErrorResetBoundary();

  const handleTabMenuSelect: MouseEventHandler<HTMLButtonElement> = (event) => {
    setSelectedTabMenu(event.currentTarget.value);
  };

  useDebounce(
    () => {
      setDebouncedSearchQuery(searchQuery);
    },
    200,
    [searchQuery]
  );

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, []);

  return (
    <>
      <SearchSection>
        <form onSubmit={handleSearch}>
          <Input
            customWidth="100%"
            placeholder={getInputPlaceholder(selectedTabMenu)}
            rightIcon={
              <Button customHeight="36px" color="white">
                <SvgIcon variant="search" />
              </Button>
            }
            value={searchQuery}
            onChange={handleSearchQuery}
            ref={inputRef}
          />
        </form>
        {!isSubmitted && debouncedSearchQuery && isAutocompleteOpen && (
          <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
            <Suspense fallback={<Loading />}>
              <RecommendList
                searchQuery={debouncedSearchQuery}
                handleSearchClick={handleSearchClick}
                handleAutocompleteClose={handleAutocompleteClose}
              />
            </Suspense>
          </ErrorBoundary>
        )}
      </SearchSection>
      <Spacing size={20} />
      <TabMenu
        tabMenus={SEARCH_PAGE_TABS}
        selectedTabMenu={selectedTabMenu}
        handleTabMenuSelect={handleTabMenuSelect}
      />
      <SearchResultSection>
        {isSubmitted && debouncedSearchQuery ? (
          <>
            <Heading as="h2" size="lg" weight="regular">
              <Mark>&apos;{searchQuery}&apos;</Mark>에 대한 검색결과입니다.
            </Heading>
            <ErrorBoundary fallback={ErrorComponent}>
              <Suspense fallback={<Loading />}>
                <Spacing size={20} />
                {isProductSearchTab(selectedTabMenu) ? (
                  <ProductSearchResultList searchQuery={debouncedSearchQuery} />
                ) : (
                  <RecipeSearchResultList searchQuery={debouncedSearchQuery} />
                )}
              </Suspense>
            </ErrorBoundary>
          </>
        ) : (
          <Text>{selectedTabMenu}을 검색해보세요.</Text>
        )}
      </SearchResultSection>
    </>
  );
};

export default SearchPage;

const SearchSection = styled.section`
  position: relative;
`;

const SearchResultSection = styled.section`
  margin-top: 30px;
`;

const Mark = styled.mark`
  font-weight: ${({ theme }) => theme.fontWeights.bold};
  background-color: ${({ theme }) => theme.backgroundColors.default};
`;
