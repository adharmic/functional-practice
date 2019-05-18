let rec pow x n =
  if n == 0
  then 1
  else if n == 1
  then x
  else x * pow x (n-1)
;;

let rec float_pow x n =
  if n == 0
  then 1.0
  else if n == 1
  then x
  else x *. float_pow x (n-1)
;;

let reverse : 'a list -> 'a list  =
  let rec add_head cut l =
    match l with
    | [] -> cut
    | h::t -> add_head (h::cut) t in
  fun l -> add_head [] l;;
;;

let rec compress = function
  | x :: (y :: _ as t) -> if x = y
                          then compress t
                          else x :: compress t
  | less -> less
;;

let cluster l =
    let rec group_ele current acc = function
      | [] -> []
      | [x] -> (x :: current) :: acc
      | a :: (b :: _ as t) ->
         if a = b
         then group_ele (a :: current) acc t
         else group_ele [] ((a :: current) :: acc) t in
    List.rev (group_ele [] [] l)
;;

let slice l i j =
    let rec take n = function
      | [] -> []
      | h :: t -> if n = 0
                  then []
                  else h :: take (n-1) t in
    let rec drop n = function
      | [] -> []
      | h :: t as q -> if n = 0
                       then q
                       else drop (n-1) t in
    take (j - i + 1) (drop i l)
;;

let composition f g =
  fun x -> f (g x)
;;

let rec equiv_on f g lst =
  match lst with
  | [] -> true
  | h :: t -> if f h == g h
              then equiv_on f g t
              else false
;;

let rec pairwisefilter cmp lst =
  match lst with
  | [] -> []
  | _ :: [] -> []
  | h :: h1 :: t -> if cmp h h1 == h
                    then h :: pairwisefilter cmp t
                    else h1 :: pairwisefilter cmp t
;;

let rec polynomial lst =
  match lst with
  | [] -> fun x -> 0
  | (f, s) :: t -> fun x -> (pow x s) * f + (polynomial t) x
;;

let min (x: int) (y: int) =
  if x > y
  then y
  else x
;;

let rec print_list = function
  | [] -> ()
  | e::l -> print_int e ; print_string " " ; print_list l
;;

type bool_expr =
  | Lit of string
  | Not of bool_expr
  | And of bool_expr * bool_expr
  | Or of bool_expr * bool_expr
;;

let rec eval_exp a lit_a b lit_b = function
  | Lit(x) -> if x = a
              then lit_a
              else lit_b
  | And(x, y) -> eval_exp a lit_a b lit_b x && eval_exp a lit_a b lit_b y
  | Not(x) -> not(eval_exp a lit_a b lit_b x)
  | Or(x, y) -> eval_exp a lit_a b lit_b x || eval_exp a lit_a b lit_b y
let truth_table a b exp =
  [(true, true, eval_exp a true b true exp); (true, false, eval_exp a true b false exp); (false, true, eval_exp a false b true exp); (false, false, eval_exp a false b false exp)]
;;

type 'a  binary_tree =
  | Node of 'a * 'a binary_tree * 'a binary_tree
  | Empty
;;

let rec tree2str = function
    | Empty -> ""
    | Node(value, nodeL, nodeR) ->
       let value = string_of_int value in
       match nodeL, nodeR with
       | Empty, Empty -> value
       | _, _ -> value ^ "(" ^ (tree2str nodeL)
                 ^ "," ^ (tree2str nodeR) ^ ")"
;;
