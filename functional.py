from functools import reduce


def flatten(fatty):
    if not fatty:
        return fatty
    if isinstance(fatty[0], list):
        return flatten(fatty[0]) + flatten(fatty[1:])
    return fatty[:1] + flatten(fatty[1:])


def reverse(unordered):
    if not unordered:
        return []
    if len(unordered) == 1:
        if isinstance(unordered[0], list):
            return [reverse(unordered[0])]
        else:
            return unordered
    return reverse(unordered[1:]) + reverse(unordered[:1])


def compress(redund):
    if redund:
        orig = redund[0]
        for index, value in enumerate(redund):
            if value != orig:
                return [orig] + compress(redund[index:])
        return [orig]
    else:
        return []


def capitalized(items: list) -> list:
    return list(filter(lambda cap: cap[0].isupper(), items))


def longest(strings: list, from_start=True) -> object:
    return list(filter(lambda x: len(x) == len(max(strings)), strings))[0] if from_start else max(strings)


def composition(func1, func2):
    return lambda result: func2(func1(result))


def n_composition(*functions: list):
    return reduce((lambda x, y: composition(x, y)), *functions)
