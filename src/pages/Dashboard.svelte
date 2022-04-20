<script>
    import { onMount } from "svelte";
    import Topbar from "../components/Topbar.svelte";
    import Footer from "../components/Footer.svelte";
    import Section from "../components/Section.svelte";
    import Card from "../components/Card.svelte";
    import Placeholder from "../components/Placeholder.svelte";
    import TodoModal from "../components/TodoModal.svelte";

    let todoItem = '';
    let todoModalText = '';
    let todoModalStatus = '';
    let todoModal;
    let todoList = [];

    onMount(() => {
        if (localStorage.getItem("todoList")) {
            todoList = JSON.parse(localStorage.getItem("todoList"));
        }
    });

    const addTodo = () => {
		todoList = [...todoList, {todo: todoItem, status: false}];
		todoItem = '';
        localStorage.setItem('todoList', JSON.stringify(todoList));
    };

    const openTodo = (index) => {
        todoModal = !todoModal;
        todoModalText = todoList[index].todo;
        todoModalStatus = todoList[index].status;
    };

    const removeTodoFromList = (index) => {
		todoList.splice(index, 1);
		todoList = todoList;
        localStorage.setItem('todoList', JSON.stringify(todoList));
    }

    const todoComplete = (index) => {
        todoList[index].status = !todoList[index].status;
        localStorage.setItem('todoList', JSON.stringify(todoList))
    }

    const refreshTodoList = () => {
        todoList = JSON.parse(localStorage.getItem("todoList"));
    }
</script>

<Topbar on:refresh={refreshTodoList} />
<Section>
    {#if todoList.length >= 1}    
        {#each todoList as item, index}
            <Card 
                on:click={() => openTodo(index)}
                on:delete={() => removeTodoFromList(index)} 
                on:done={() => todoComplete(index)} 
                todoStatus={todoList[index].status}
                text={item.todo}
            />
        {/each}
    {:else}
        <Placeholder />
    {/if}
</Section>
<Footer bind:todoItem on:todo={addTodo} />
<TodoModal bind:todoModal bind:todoModalText bind:todoModalStatus/>

<style>

</style>