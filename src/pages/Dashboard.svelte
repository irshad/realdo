<script>
    import { onMount } from "svelte";
    import Topbar from "../components/Topbar.svelte";
    import Footer from "../components/Footer.svelte";
    import Section from "../components/Section.svelte";
    import Card from "../components/Card.svelte";
    import Placeholder from "../components/Placeholder.svelte";
    import TodoModal from "../components/TodoModal.svelte";
    import Search from "../components/Search.svelte";
    import Tabs from "../components/tabs/Tabs.svelte";
    import Tab from "../components/tabs/Tab.svelte";
    import TabPanel from "../components/tabs/TabPanel.svelte";

    let id = '';
    let todoItem = '';
    let todoList = [];
    let pendingTodoList = [];
    let completedTodoList = [];
    let todoModal;
    let todoModalID = '';
    let todoModalText = '';
    let todoModalStatus = '';
    let showSearch = false;
    let searchValue;
    let searchList = [];
    let tab;

    onMount(() => {
        if (localStorage.getItem("todoList")) {
            todoList = JSON.parse(localStorage.getItem("todoList"));
        };
    });

    $:if(localStorage.getItem("todoList")) {
        console.log('Called')
        todoList.forEach((item, index) => {
            completedTodoList = todoList.filter(item => item.status == true);
        });

        todoList.forEach((item, index) => {
            pendingTodoList = todoList.filter(item => item.status == false);
        });
    }

    const mongoObjectId = () => {
		id = (new Date().getTime() / 1000 | 0).toString(16);
		id = id + 'xxxxxxxxxxxxxxxx' .replace(/[x]/g, function() {
				return (Math.random() * 16 | 0).toString(16);
		}).toLowerCase();
	};

    const addTodo = () => {
        tab = 1;
        mongoObjectId();
		todoList = [...todoList, {id: id, todo: todoItem, status: false}];
		todoItem = '';
        localStorage.setItem('todoList', JSON.stringify(todoList));
        document.getElementById("tab-panel").scrollTop = document.getElementById("tab-panel").scrollHeight + 80;
    };

    const removeTodoFromList = (index) => {
        let currentTodo = todoList.findIndex(todo => todo.id === index);
		todoList.splice(currentTodo, 1);
		todoList = todoList;
        localStorage.setItem('todoList', JSON.stringify(todoList));
    };

    const removeSearchTodoFromList = (index) => {
        let currentTodo = searchList.findIndex(todo => todo.id === index);
		searchList.splice(currentTodo, 1);
        searchList = searchList;
        removeTodoFromList(index);
    }

    const todoComplete = (index) => {
        let currentTodo = todoList.filter(todo => todo.id === index)[0];
        currentTodo.status = !currentTodo.status;
        todoList = todoList;
        searchList = searchList;
        localStorage.setItem('todoList', JSON.stringify(todoList));
    }

    const openTodo = (index) => {
        todoModal = !todoModal;
        let currentTodo = todoList.filter(todo => todo.id === index)[0];
        todoModalID = currentTodo.id;
        todoModalText = currentTodo.todo;
        todoModalStatus = currentTodo.status;
    };

    const updateTodo = () => {
        todoList.forEach((item, index) => {
            if (item.id === todoModalID) {
                todoList[index].todo = todoModalText;
                todoList[index].status = todoModalStatus;
            }
        });
        localStorage.setItem('todoList', JSON.stringify(todoList));
        todoList = todoList;
        searchList = searchList;
    };

    const search = () => {
        searchList = [];
        if(searchValue != '') {
            todoList.forEach((item) => {
                if (item.todo.toLowerCase().includes(searchValue.toLowerCase()) && searchValue !== '') {
                    searchList.push(item);
                }
            });
        }
        return searchList;
    };

    $:searchValue,(() => {
        if(searchValue == '') {
            searchList = [];
        }
    })();
</script>

<Topbar on:openSearch={() => showSearch = !showSearch} />
<Section>
    <Tabs>
        <Tab label="one" bind:tab value="1">Todo</Tab>
        <Tab label="two" bind:tab value="2">Completed</Tab>
    </Tabs>

    <TabPanel {tab} value="1">
        {#if pendingTodoList.length >= 1}
            {#each pendingTodoList as item}
                <Card
                    on:click={() => openTodo(item.id)}
                    on:delete={() => removeTodoFromList(item.id)}
                    on:done={() => todoComplete(item.id)}
                    todoStatus={item.status}
                    text={item.todo}
                    search={false}
                />
            {/each}
        {:else}
            <Placeholder />
        {/if}
    </TabPanel>

    <TabPanel {tab} value="2">
        {#if completedTodoList.length >= 1}
            {#each completedTodoList as item}
                <Card
                    on:click={() => openTodo(item.id)}
                    on:delete={() => removeTodoFromList(item.id)}
                    on:done={() => todoComplete(item.id)}
                    todoStatus={item.status}
                    text={item.todo}
                    search={false}
                />
            {/each}
        {:else}
            <Placeholder />
        {/if}
    </TabPanel>
</Section>

<Footer bind:todoItem on:todo={addTodo} />
<TodoModal bind:todoModal bind:todoModalText bind:todoModalStatus bind:todoModalID on:updateTodo={updateTodo}/>

<Search bind:showSearch on:search={search} bind:searchValue>
    {#if searchList.length >= 1}
        {#each searchList as item}
            <Card
                on:click={() => openTodo(item.id)}
                on:delete={() => removeSearchTodoFromList(item.id)}
                on:done={() => todoComplete(item.id)}
                todoStatus={item.status}
                text={item.todo}
                search={true}
            />
        {/each}
    {:else}
        <Placeholder/>
    {/if}
</Search>

<style>

</style>
