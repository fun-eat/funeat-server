import type { Member } from './member';
import type { Product } from './product';

export interface RecipeRequest {
  title: string;
  productIds: number[];
  content: string;
}

export interface RecipePostRequestBody extends FormData {
  images: File[];
  recipeRequest: RecipeRequest;
}

export type RecipeRequestKey = keyof RecipeRequest;

export interface RecipeDetail extends Recipe {
  images: string[];
  content: string;
  totalPrice: number;
  favorite: boolean;
}

export interface BaseRecipe {
  id: number;
  image: string;
  title: string;
  createdAt: string;
  favoriteCount: number;
}

export interface Recipe extends BaseRecipe {
  author: Member;
  products: RecipeProductWithPrice[];
}

export interface MemberRecipe extends BaseRecipe {
  products: RecipeProduct[];
}

export interface RecipeFavoriteRequestBody {
  favorite: boolean;
}

type RecipeProductWithPrice = Pick<Product, 'id' | 'name' | 'price'>;
export type RecipeProduct = Omit<RecipeProductWithPrice, 'price'>;