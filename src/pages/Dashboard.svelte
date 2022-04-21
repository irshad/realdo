<script>
    import { onMount } from "svelte";
    import Topbar from "../components/Topbar.svelte";
    import Footer from "../components/Footer.svelte";
    import Section from "../components/Section.svelte";
    import Card from "../components/Card.svelte";
    import Placeholder from "../components/Placeholder.svelte";
    import TodoModal from "../components/TodoModal.svelte";

    let id = '';
    let todoItem = '';
    let todoList = [];
    let todoModal;
    let todoModalID = '';
    let todoModalText = '';
    let todoModalStatus = '';

    onMount(() => {
        if (localStorage.getItem("todoList")) {
            todoList = JSON.parse(localStorage.getItem("todoList"));
        }
    });

    const mongoObjectId = () => {
		id = (new Date().getTime() / 1000 | 0).toString(16);
		id = id + 'xxxxxxxxxxxxxxxx' .replace(/[x]/g, function() {
				return (Math.random() * 16 | 0).toString(16);
		}).toLowerCase();
	};

    const addTodo = () => {
        mongoObjectId();
		todoList = [...todoList, {id: id, todo: todoItem, status: false}];
		todoItem = '';
        localStorage.setItem('todoList', JSON.stringify(todoList));
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

    const openTodo = (index) => {
        todoModal = !todoModal;
        todoModalID = todoList[index].id;
        todoModalText = todoList[index].todo;
        todoModalStatus = todoList[index].status;
    };

    const updateTodo = () => {
        todoList.forEach((item, index) => {
            if (item.id === todoModalID) {
                todoList[index].todo = todoModalText;
                todoList[index].status = todoModalStatus;
            }
        });
        localStorage.setItem('todoList', JSON.stringify(todoList));
    };

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
<TodoModal bind:todoModal bind:todoModalText bind:todoModalStatus bind:todoModalID on:updateTodo={updateTodo}/>

<style>

</style>